package com.ui.controller;

import com.ui.tools.FxmlFileOpener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

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
    public Button playButton;

    public void initialize() {
        AccesController.setTrackPlayerHelper();
        FxmlFileOpener.loadFrame(stackPane, "album_panel.fxml");
    }

    @FXML
    public void skipTrack(){}

    @FXML
    public void goBackTrack(){}

    @FXML
    public void playTrack(){
        AccesController.getTrackPlayerHelper().playTrack();
    }

    @FXML
    public void muteUnmute(){}

    @FXML
    public void openExplorePanel(){}

    @FXML
    public void openArtistPanel(){}

    @FXML
    public void openAlbumPanel(){}

    @FXML
    public void openSettingsPanel(){}

    @FXML
    public void openPlaylistPanel(){}
}
