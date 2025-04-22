package com.ui.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AlbumPanelController {
    @FXML
    public VBox trackListVBox;
    @FXML
    public Label playlistNameLabel;
    @FXML
    public Label playlistTracksNumberLabel;
    @FXML
    public Label errorLabel;

    public void initialize() {
        AccesController.setAlbumPanelController(this);
    }
}
