package com.ui.controller.main_panel;

import com.ui.controller.AccessController;
import com.ui.controller.container.TrackUiContainer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

public class NavBarController {
    @FXML
    public Slider volumeSlider;
    @FXML
    public Slider trackSlider;
    @FXML
    public Label trackNameLabel;
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
    public ProgressBar trackProgressBar;
    @FXML
    public ProgressBar volumeProgressBar;

    private static TrackUiContainer trackUiContainer;
    private AccessController accessController;

    public void initialize() {
        accessController = AccessController.getInstance();
        trackUiContainer = setTrackUiContainer();
        accessController.setTrackUiContainer(trackUiContainer);
    }

    @FXML
    public void skipTrack() {
        accessController.getMusicLibraryService().skip();
    }

    @FXML
    public void goBackTrack() {
        accessController.getMusicLibraryService().previous();
    }

    @FXML
    public void playPauseTrack() {
        accessController.getMusicLibraryService().playPause();
    }

    @FXML
    public void muteUnmute() {
        accessController.getMusicLibraryService().mute();
    }

    private TrackUiContainer setTrackUiContainer() {
        TrackUiContainer trackUiContainer = new TrackUiContainer();
        trackUiContainer.setTrackNameLabel(trackNameLabel);
        trackUiContainer.setArtistLabel(artistLabel);
//        trackUiContainer.setAlbumNameLabel(albumLabel);
        trackUiContainer.setTrackDurationLabel(trackDurationLabel);
        trackUiContainer.setVolumeLabel(volumeLabel);

        trackUiContainer.setMuteButton(muteButton);
        trackUiContainer.setPlayPauseButton(playPauseButton);

        trackUiContainer.setTrackSlider(trackSlider);
        trackUiContainer.setVolumeSlider(volumeSlider);

        trackUiContainer.setTrackProgressBar(trackProgressBar);
        trackUiContainer.setVolumeProgressBar(volumeProgressBar);

        trackUiContainer.setProgressBarsInSync();

        return trackUiContainer;
    }
}
