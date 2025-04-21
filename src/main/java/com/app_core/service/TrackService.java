package com.app_core.service;

import com.app_core.utils.Track;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import java.io.File;


public class TrackService {
    public TrackService() {
    }


//    public void getTrack(Button button, Label label) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Open Media File");
//
//        // Set file filters for audio
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav")
//        );
//
//        File file = fileChooser.showOpenDialog(button.getScene().getWindow());
//
//        if (file != null) {
//            this.file = file;
//            String mediaPath = file.toURI().toString();
//            label.setText(mediaPath);
//            setMediaPlayer(mediaPath);
//        }
//    }
}