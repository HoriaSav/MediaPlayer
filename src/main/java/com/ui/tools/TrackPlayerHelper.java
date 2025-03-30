package com.ui.tools;

import com.ui.controller.AccesController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;


public class TrackPlayerHelper {
    private MediaPlayer mediaPlayer;
    private File file;

    public TrackPlayerHelper() {
    }

    public void getTrack(Button button, Label label) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Media File");

        // Set file filters for audio
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav")
        );

        File file = fileChooser.showOpenDialog(button.getScene().getWindow());

        if (file != null) {
            this.file = file;
            String mediaPath = file.toURI().toString();
            label.setText(mediaPath);
            setMediaPlayer(mediaPath);
        }
    }

    private void setMediaPlayer(String mediaPath) {
        Media media = new Media(mediaPath);

        // Stop current media if something is already playing
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaPlayer = new MediaPlayer(media);
        System.out.println("Media loaded and ready to play.");

        setUpVolumeSlider(AccesController.getTrackUiContainer().getVolumeSlider());

        mediaPlayer.setOnReady(() -> {
            setupSongProgressBinding(AccesController.getTrackUiContainer().getTrackSlider());
            setTrackInfoInUi();
        });
    }

    public void playPauseTrack() {
        if (mediaPlayer != null) {
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
                setPlayPauseButtonImage("/icons/play.png");
            } else {
                mediaPlayer.play();
                setPlayPauseButtonImage("/icons/pause.png");
            }
        }
    }

    private void setPlayPauseButtonImage(String imagePath) {
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView view = new ImageView(img);
        view.setFitWidth(30);  // optional: scale it
        view.setFitHeight(30);
        AccesController.getTrackUiContainer().getPlayPauseButton().setGraphic(view);
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
        AccesController.getTrackUiContainer().setTrackInfoInUI(file);
        AccesController.getTrackUiContainer().getTrackSlider().setMax(getDuration().toSeconds());
        AccesController.getTrackUiContainer().setTrackDurationLabel(getDuration());
    }
}