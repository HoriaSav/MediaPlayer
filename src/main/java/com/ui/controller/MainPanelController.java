package com.ui.controller;

import com.ui.controller.container.TrackUiContainer;
import com.ui.tools.FxmlFileOpener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainPanelController {
    @FXML
    public Slider volumeSlider;
    @FXML
    public Slider songSlider;

    @FXML
    public StackPane stackPane;

    @FXML
    public Label trackName;
    @FXML
    public Label albumName;
    @FXML
    public Label songDuration;

    @FXML
    public Button playButton;

    TrackUiContainer trackUiContainer;

    public void initialize() {
        AccesController.setTrackPlayerHelper();
        FxmlFileOpener.loadFrame(stackPane, "album_panel.fxml");
    }

    @FXML
    public void skipTrack() {
    }

    @FXML
    public void goBackTrack() {
    }

    @FXML
    public void playTrack() {
        if (trackUiContainer == null) {
            trackUiContainer = new TrackUiContainer(songSlider, volumeSlider, songDuration);
        }
        AccesController.getTrackPlayerHelper().playTrack(trackUiContainer);
    }

    @FXML
    public void muteUnmute() {
    }

    @FXML
    public void openExplorePanel() {
    }

    @FXML
    public void openArtistPanel() {
    }

    @FXML
    public void openAlbumPanel() {
    }

    @FXML
    public void openSettingsPanel() {
    }

    @FXML
    public void openPlaylistPanel() {
    }

    @FXML
    private void minimizeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void closeApp() {
        Platform.exit();
    }

    @FXML
    private void toggleMaximize(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setMaximized(!stage.isMaximized());
    }
}
