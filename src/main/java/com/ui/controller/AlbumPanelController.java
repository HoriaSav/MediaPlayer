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

    public AccessController accessController;

    public void initialize() {
        accessController = AccessController.getInstance();
        accessController.setAlbumPanelController(this);
    }
}
