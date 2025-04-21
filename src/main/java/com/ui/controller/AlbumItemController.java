package com.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AlbumItemController {
    @FXML
    public Label trackNameLabel;
    @FXML
    public Label artistLabel;
    @FXML
    public Label albumLabel;
    @FXML
    public Label durationLabel;
    @FXML
    public Label trackNumberLabel;
    @FXML
    public Button playTrackButton;

    public void initialize() {
    }

    public void setTrackItem(String trackName, String artist, String album, int duration, int trackNumber){
        trackNameLabel.setText(trackName);
        artistLabel.setText(artist);
        albumLabel.setText(album);
        durationLabel.setText(String.valueOf(duration));
        trackNumberLabel.setText(String.valueOf(trackNumber));
    }

    public Button getPlayTrackButton() {
        return playTrackButton;
    }
}
