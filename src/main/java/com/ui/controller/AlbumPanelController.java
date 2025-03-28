package com.ui.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class AlbumPanelController {
    @FXML
    public Label fileLocationLabel;
    @FXML
    public Button getFileLocationButton;

    public void initialize() {
    }

    @FXML
    public void setFileLocation() {
        AccesController.getTrackPlayerHelper().getTrack(getFileLocationButton);
    }
}
