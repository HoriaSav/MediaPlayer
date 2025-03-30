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
    public Slider trackSlider;

    @FXML
    public StackPane stackPane;

    @FXML
    public Label trackNameLabel;
    @FXML
    public Label albumLabel;
    @FXML
    public Label artistLabel;
    @FXML
    public Label trackDurationLabel;
    @FXML
    public Label volumeLabel;

    @FXML
    public Button playPauseButton;
    @FXML
    public Button muteButton;

    @FXML
    public ProgressBar trackProgressbar;
    @FXML
    public ProgressBar volumeProgressBar;

    private static TrackUiContainer trackUiContainer;

    public void initialize() {
        AccesController.setTrackPlayerHelper();
        FxmlFileOpener.loadFrame(stackPane, "album_panel.fxml");
        trackUiContainer = setTrackUiContainer();
        AccesController.setTrackUiContainer(trackUiContainer);
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

    public TrackUiContainer getTrackUiContainer() {
        return trackUiContainer;
    }

    private TrackUiContainer setTrackUiContainer() {
        TrackUiContainer trackUiContainer = new TrackUiContainer();
        trackUiContainer.setTrackNameLabel(trackNameLabel);
        trackUiContainer.setArtistLabel(artistLabel);
        trackUiContainer.setAlbumNameLabel(albumLabel);
        trackUiContainer.setTrackDurationLabel(trackDurationLabel);
        trackUiContainer.setVolumeLabel(volumeLabel);

        trackUiContainer.setMuteButton(muteButton);
        trackUiContainer.setPlayPauseButton(playPauseButton);

        trackUiContainer.setTrackSlider(trackSlider);
        trackUiContainer.setVolumeSlider(volumeSlider);

        trackUiContainer.setTrackProgressBar(trackProgressbar);
        trackUiContainer.setVolumeProgressBar(volumeProgressBar);

        trackUiContainer.setProgressBarsInSync();

        return trackUiContainer;
    }
}
