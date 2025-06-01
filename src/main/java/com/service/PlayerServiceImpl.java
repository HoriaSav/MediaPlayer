package com.service;

import com.exception.MediaPlaybackException;
import com.exception.TrackNotFoundException;
import com.model.CurrentMedia;
import com.repository.basicservice.interfaces.Track;
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

public class PlayerServiceImpl implements PlayerService {
    private MediaPlayer mediaPlayer;
    private List<Track> playlist;
    private int currentTrackIndex = -1;
    private final AccessController accessController;

    private final CurrentMedia currentMedia;

    public PlayerServiceImpl(CurrentMedia currentMedia) {
        accessController = AccessController.getInstance();
        this.currentMedia = currentMedia;
    }

    /**
     * plays track in player, when playTrack button on track is pressed
     */
    @Override
    public void play(String trackName) {
        if (playlist == null || playlist.isEmpty()) {
            playlist = currentMedia.getTrackList();
        }

        if (trackName.isEmpty()) {
            currentTrackIndex = 0;
            playTrackAtCurrentIndex();
        } else {
            boolean trackFound = false;
            for (Track track : playlist) {
                if (track.getName().equals(trackName)) {
                    currentTrackIndex = playlist.indexOf(track);
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

    /**
     * pauses/plays track in player, when playTrack/pause button in navbar is pressed
     */
    @Override
    public void playPauseTrack() {
        try {
            InputValidator.validateMediaPlayerState(mediaPlayer, currentMedia.getCurrentTrack());

            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                setPlayPauseButtonImage("/icons/play.png");
            } else {
                mediaPlayer.play();
                setPlayPauseButtonImage("/icons/pause.png");
            }
        } catch (Exception e) {
            throw new MediaPlaybackException("Cannot playTrack/pause track", e);
        }
    }

    private void playTrackAtCurrentIndex() {
        if (currentTrackIndex >= 0 && currentTrackIndex < playlist.size()) {
            currentMedia.setCurrentTrack(playlist.get(currentTrackIndex));
            String mediaPath = currentMedia.getCurrentTrack().getPath();
            setMediaPlayer(mediaPath);

            // Set up listener for track completion
            mediaPlayer.setOnEndOfMedia(this::playNextTrack);

            // Start playing
            mediaPlayer.play();
            setPlayPauseButtonImage("/icons/pause.png");
        }
    }

    @Override
    public void playNextTrack() {
        if (playlist != null && !playlist.isEmpty() && hasNextTrack()) {
            currentTrackIndex = (currentTrackIndex + 1) % playlist.size();
            playTrackAtCurrentIndex();
        }
    }

    private boolean hasNextTrack() {
        return playlist != null && currentTrackIndex < playlist.size() - 1;
    }

    @Override
    public void playPreviousTrack() {
        if (playlist != null && !playlist.isEmpty() && hasPreviousTrack()) {
            currentTrackIndex = (currentTrackIndex - 1 + playlist.size()) % playlist.size();
            playTrackAtCurrentIndex();
        }
    }

    private boolean hasPreviousTrack() {
        return playlist != null && currentTrackIndex > 0;
    }

    @Override
    public void mute() {
        //TODO: implement mute functionality
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

    //TODO: move to UI related class
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

    //TODO: move to UI related class
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

    //TODO: move to UI related class
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

    //TODO: move to UI related class
    private void setTrackInfoInUi() {
        accessController.getTrackUiContainer().setTrackInfoInUI(currentMedia.getCurrentTrack());
        accessController.getTrackUiContainer().getTrackSlider().setMax(getDuration().toSeconds());
        accessController.getTrackUiContainer().setTrackDurationLabel(getDuration());
    }

    public String getCurrentTrackNumber() {
        if (playlist != null && !playlist.isEmpty()) {
            return String.format("%d/%d", currentTrackIndex + 1, playlist.size());
        }
        return "0/0";
    }

    public void selectCurrentTrack(Track track) {
        if (track != null) {
            currentMedia.setCurrentTrack(track);
            String mediaPath = track.getPath();
            setMediaPlayer(mediaPath);
        }
        playPauseTrack();
    }
}