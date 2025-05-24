package com.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PlaylistItemController {
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
    @FXML
    public Button likedTrackButton;

    boolean liked = false;

    public void initialize() {
    }

    public void loadTrackItem(String trackName, String artist, String album, int duration, int trackNumber) {
        trackNameLabel.setText(trackName);
        artistLabel.setText(artist);
        albumLabel.setText(album);
        durationLabel.setText(String.valueOf(duration));
        trackNumberLabel.setText(String.valueOf(trackNumber));
    }

    public Button getPlayTrackButton() {
        return playTrackButton;
    }

    @FXML
    public void likeTrack() {
        liked = !liked;
        String imagePath;
        if (liked) {
            imagePath = "/icons/heart_filled.png";
        } else {
            imagePath = "/icons/heart_empty.png";
        }
        Image img = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
        ImageView view = new ImageView(img);
        view.setFitWidth(17);  // optional: scale it
        view.setFitHeight(17);
        likedTrackButton.setGraphic(view);
    }
}
