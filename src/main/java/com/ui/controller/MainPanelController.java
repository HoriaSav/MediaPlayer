package com.ui.controller;

import com.model.Track;
import com.ui.controller.container.TrackUiContainer;
import com.ui.tools.FxmlFileOpener;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

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

    @FXML
    public VBox playlistVBox;

    private static TrackUiContainer trackUiContainer;

    public void initialize() {
        AccesController.setTrackPlayerHelper();
        FxmlFileOpener.loadFrame(stackPane, "album_panel.fxml");
        trackUiContainer = setTrackUiContainer();
        AccesController.setTrackUiContainer(trackUiContainer);
    }

    @FXML
    public void skipTrack() {
        if (AccesController.getPlayerService().hasNextTrack()) {
            AccesController.getPlayerService().playNextTrack();
        }
    }

    @FXML
    public void goBackTrack() {
        if (AccesController.getPlayerService().hasPreviousTrack()) {
            AccesController.getPlayerService().playPreviousTrack();
        }
    }

    @FXML
    public void playPauseTrack() {
        AccesController.getPlayerService().playPauseTrack();
    }

    public void loadFolder() {
        try {
            AccesController.getAlbumPanelController().playlistNameLabel.setText(AccesController.getPlayerService().getFolderName());
            List<Track> trackList = AccesController.getPlayerService().getCurrentFolderTracks();
            AccesController.getAlbumPanelController().playlistTracksNumberLabel.setText("Tracks: " + trackList.size());
            AccesController.getAlbumPanelController().trackListVBox.getChildren().clear();
            for (Track track : trackList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/album_item_panel.fxml"));
                Node itemNode = loader.load();
                AlbumItemController albumItemController = loader.getController();
                albumItemController.loadTrackItem(track.getName(), track.getArtist(), track.getAlbum(), track.getDuration(), track.getDuration());
                albumItemController.getPlayTrackButton().setOnAction(_ -> {
                    selectTrack(track.getName());
                });
                AccesController.getAlbumPanelController().trackListVBox.getChildren().add(itemNode);
            }
        } catch (IOException e) {
        }
    }

    public void selectTrack(String trackName) {
        AccesController.getPlayerService().playCurrentFolder(trackName);
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
    public void addNewPlaylist() {
        AccesController.getPlayerService().addFolder();
        Button button = new Button();
        String folderName = AccesController.getPlayerService().getFolderName();
        button.setText(folderName);
        button.setOnAction(_ -> {
            setCurrentPlaylist(button.getText());
        });
        playlistVBox.getChildren().add(button);
        loadFolder();
    }

    public void setCurrentPlaylist(String playlistName) {

        AccesController.getPlayerService().setCurrentFolder(playlistName);
        loadFolder();
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
