package com.ui.controller;
import com.repository.basicservice.interfaces.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class PlaylistPanelController {
    @FXML
    public ListView<Track> trackListView;
    @FXML
    public Label playlistNameLabel;
    @FXML
    public Label playlistTracksNumberLabel;

    public AccessController accessController;

    public void initialize() {
        accessController = AccessController.getInstance();
        accessController.setAlbumPanelController(this);
    }
}
