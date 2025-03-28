package com.ui.tools;

import com.ui.controller.container.TrackUiContainer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class TrackPlayerHelper {
    private MediaPlayer mediaPlayer;

    public TrackPlayerHelper() {
    }

    public void getTrack(Button button) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Media File");

        // Set file filters for audio
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav")
        );

        File file = fileChooser.showOpenDialog(button.getScene().getWindow());

        if (file != null) {
            String mediaPath = file.toURI().toString();
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
    }

    public void playTrack(TrackUiContainer trackUiContainer) {
        if (mediaPlayer != null) {
            setupProgressBinding(trackUiContainer.getSongSlider());
            setUpDurationLabel(trackUiContainer.getSongDurationLabel());
            mediaPlayer.play();
        }
    }

    private void setUpDurationLabel(Label songDurationLabel) {
        Duration time = mediaPlayer.getMedia().getDuration();
        songDurationLabel.setText(time.toMinutes() + " ");
    }

    private void setupProgressBinding(Slider progressSlider) {
        mediaPlayer.setOnReady(() -> {
            Duration total = mediaPlayer.getMedia().getDuration();
            progressSlider.setMax(total.toSeconds());
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progressSlider.isValueChanging()) { // avoid conflict when user is dragging
                progressSlider.setValue(newTime.toSeconds());
            }
        });

        progressSlider.setOnMouseReleased(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            }
        });

        progressSlider.valueChangingProperty().addListener((obs, wasChanging, isChanging) -> {
            if (!isChanging && mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            }
        });

        progressSlider.setOnMousePressed(event -> {
            if (mediaPlayer != null) {
                mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            }
        });
    }
}
