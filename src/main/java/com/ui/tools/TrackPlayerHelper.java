package com.ui.tools;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

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

    public void playTrack() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }
}
