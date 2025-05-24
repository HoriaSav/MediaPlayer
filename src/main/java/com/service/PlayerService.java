package com.service;

import com.exception.MediaPlaybackException;
import com.exception.TrackNotFoundException;
import com.model.Track;
import com.ui.controller.AccessController;
import com.util.InputValidator;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class PlayerService {
    private final FolderService folderService;
    private MediaPlayer mediaPlayer;
    private Track currentTrack;
    private List<Track> playList;
    private int currentTrackIndex = -1;
    private final AccessController accessController;


    public PlayerService() {
        this.folderService = new FolderService();
        accessController = AccessController.getInstance();
    }

    public String getFolderName() {
        return folderService.getCurrentFolder().getFolderName();
    }

    public void setCurrentFolder(String folderName) {
        folderService.setCurrentFolder(folderName);
    }


    public void playCurrentFolder(String trackName) throws MediaPlaybackException {
        playList = folderService.getCurrentFolderTracks();
        if (playList.isEmpty()) {
            throw new MediaPlaybackException("No tracks available in the current folder");
        }

        if (trackName.isEmpty()) {
            currentTrackIndex = 0;
            playTrackAtCurrentIndex();
        } else {
            boolean trackFound = false;
            for (Track track : playList) {
                if (track.getName().equals(trackName)) {
                    currentTrackIndex = playList.indexOf(track);
                    playTrackAtCurrentIndex();
                    trackFound = true;
                    break;
                }
            }
            if (!trackFound) {
                throw new TrackNotFoundException("Track not found: " + trackName);
            }
        }
    }

    public void playPauseTrack() {
        try {
            InputValidator.validateMediaPlayerState(mediaPlayer, currentTrack);

            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                setPlayPauseButtonImage("/icons/play.png");
            } else {
                mediaPlayer.play();
                setPlayPauseButtonImage("/icons/pause.png");
            }
        } catch (Exception e) {
            throw new MediaPlaybackException("Cannot play/pause track", e);
        }
    }

    private void playTrackAtCurrentIndex() {
        if (currentTrackIndex >= 0 && currentTrackIndex < playList.size()) {
            currentTrack = playList.get(currentTrackIndex);
            String mediaPath = currentTrack.getPath();
            setMediaPlayer(mediaPath);

            // Set up listener for track completion
            mediaPlayer.setOnEndOfMedia(this::playNextTrack);

            // Start playing
            mediaPlayer.play();
            setPlayPauseButtonImage("/icons/pause.png");
        }
    }

    public void playNextTrack() {
        if (playList != null && !playList.isEmpty()) {
            currentTrackIndex = (currentTrackIndex + 1) % playList.size();
            playTrackAtCurrentIndex();
        }
    }

    public void playPreviousTrack() {
        if (playList != null && !playList.isEmpty()) {
            currentTrackIndex = (currentTrackIndex - 1 + playList.size()) % playList.size();
            playTrackAtCurrentIndex();
        }
    }

    public boolean hasNextTrack() {
        return playList != null && currentTrackIndex < playList.size() - 1;
    }

    public boolean hasPreviousTrack() {
        return playList != null && currentTrackIndex > 0;
    }

    public String getCurrentTrackNumber() {
        if (playList != null && !playList.isEmpty()) {
            return String.format("%d/%d", currentTrackIndex + 1, playList.size());
        }
        return "0/0";
    }


    public void addFolder() {
        folderService.addMusicFolder();
    }

    public List<Track> getCurrentFolderTracks() {
        return folderService.getCurrentFolderTracks();
    }

    private void setMediaPlayer(String mediaPath) throws MediaPlaybackException {
        try {
            String uri = new File(mediaPath).toURI().toString();
            Media media = new Media(uri);

            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }

            mediaPlayer = new MediaPlayer(media);
            setUpVolumeSlider(accessController.getTrackUiContainer().getVolumeSlider());

            mediaPlayer.setOnReady(() -> {
                setupSongProgressBinding(accessController.getTrackUiContainer().getTrackSlider());
                setTrackInfoInUi();
            });
        } catch (Exception e) {
            throw new MediaPlaybackException("Failed to initialize media player", e);
        }
    }

    public void selectCurrentTrack(Track track) {
        if (track != null) {
            currentTrack = track;
            String mediaPath = track.getPath();
            setMediaPlayer(mediaPath);
        }
        playPauseTrack();
    }

    private void setPlayPauseButtonImage(String imagePath) {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView view = new ImageView(img);
        view.setFitWidth(30);  // optional: scale it
        view.setFitHeight(30);
        accessController.getTrackUiContainer().getPlayPauseButton().setGraphic(view);
    }

    private Duration getDuration() {
        return mediaPlayer.getMedia().getDuration();
    }

    private void setupSongProgressBinding(Slider progressSlider) {
        progressSlider.setMax(getDuration().toSeconds());

        mediaPlayer.currentTimeProperty().addListener((_, _, newTime) -> {
            if (!progressSlider.isValueChanging()) { // avoid conflict when user is dragging
                progressSlider.setValue(newTime.toSeconds());
            }
        });

        progressSlider.setOnMouseReleased(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            }
        });

        progressSlider.valueChangingProperty().addListener((_, _, isChanging) -> {
            if (!isChanging && mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            }
        });

        progressSlider.setOnMousePressed(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            }
        });
    }

    public void setUpVolumeSlider(Slider volumeSlider) {
        mediaPlayer.setVolume(volumeSlider.getValue());

        volumeSlider.valueProperty().addListener((_, _, newVal) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newVal.doubleValue());
            }
        });

        volumeSlider.setOnMousePressed(_ -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(volumeSlider.getValue());
            }
        });
    }

    private void setTrackInfoInUi() {
        accessController.getTrackUiContainer().setTrackInfoInUI(currentTrack);
        accessController.getTrackUiContainer().getTrackSlider().setMax(getDuration().toSeconds());
        accessController.getTrackUiContainer().setTrackDurationLabel(getDuration());
    }
}