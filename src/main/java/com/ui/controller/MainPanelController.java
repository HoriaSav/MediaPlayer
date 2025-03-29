package com.ui.controller;

import com.ui.controller.container.TrackUiContainer;
import com.ui.tools.FxmlFileOpener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
    public Label albumNameLabel;
    @FXML
    public Label trackNameLabel;
    @FXML
    public Label songDurationLabel;
    @FXML
    public Label volumeLabel;

    @FXML
    public Button playButton;

    @FXML
    public Button muteButton;
    @FXML
    public Label artistLabel;
    @FXML
    public ProgressBar songProgressProgressbar;
    @FXML
    public ProgressBar volumeProgressBar;

    private static TrackUiContainer trackUiContainer;

    public void initialize() {
        AccesController.setTrackPlayerHelper();
        FxmlFileOpener.loadFrame(stackPane, "album_panel.fxml");
        AccesController.setMainPanelController(this);
        setProgressBarInSync();
    }

    private void setProgressBarInSync(){
        songProgressProgressbar.progressProperty().bind(
                songSlider.valueProperty().divide(songSlider.maxProperty())
        );
        volumeProgressBar.progressProperty().bind(
                volumeSlider.valueProperty().divide(volumeSlider.maxProperty())
        );
    }

    public TrackUiContainer getTrackUiContainer() {
        if(trackUiContainer == null) {
            setTrackUiContainer();
        }
        return trackUiContainer;
    }

    private void setTrackUiContainer() {
        trackUiContainer = new TrackUiContainer(songSlider, volumeSlider, volumeLabel, songDurationLabel, trackNameLabel, albumNameLabel, artistLabel);
        trackUiContainer.setPlayPauseButton(playButton);
    }

    @FXML
    public void skipTrack() {
    }

    @FXML
    public void goBackTrack() {
    }

    @FXML
    public void playPauseTrack() {
        AccesController.getTrackPlayerHelper().playPauseTrack();
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
