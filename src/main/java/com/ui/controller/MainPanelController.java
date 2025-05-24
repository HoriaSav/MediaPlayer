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

import static javafx.geometry.Pos.CENTER_LEFT;

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
    private AccessController accessController;

    public void initialize() {
        accessController = AccessController.getInstance();
        accessController.setTrackPlayerHelper();
        FxmlFileOpener.loadFrame(stackPane, "album_panel.fxml");
        trackUiContainer = setTrackUiContainer();
        accessController.setTrackUiContainer(trackUiContainer);
    }

    @FXML
    public void skipTrack() {
        if (accessController.getPlayerService().hasNextTrack()) {
            accessController.getPlayerService().playNextTrack();
        }
    }

    @FXML
    public void goBackTrack() {
        if (accessController.getPlayerService().hasPreviousTrack()) {
            accessController.getPlayerService().playPreviousTrack();
        }
    }

    @FXML
    public void playPauseTrack() {
        accessController.getPlayerService().playPauseTrack();
    }

    public void loadFolder() {
        try {
            accessController.getAlbumPanelController().playlistNameLabel.setText(accessController.getPlayerService().getFolderName());
            List<Track> trackList = accessController.getPlayerService().getCurrentFolderTracks();
            accessController.getAlbumPanelController().playlistTracksNumberLabel.setText("Tracks: " + trackList.size());
            accessController.getAlbumPanelController().trackListVBox.getChildren().clear();
            for (Track track : trackList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/album_item_panel.fxml"));
                Node itemNode = loader.load();
                AlbumItemController albumItemController = loader.getController();
                albumItemController.loadTrackItem(track.getName(), track.getArtist(), track.getAlbum(), track.getDuration(), track.getDuration());
                albumItemController.getPlayTrackButton().setOnAction(_ -> selectTrack(track.getName()));
                accessController.getAlbumPanelController().trackListVBox.getChildren().add(itemNode);
            }
        } catch (IOException e) {
            //TODO exception handling
        }
    }

    public void selectTrack(String trackName) {
        accessController.getPlayerService().playCurrentFolder(trackName);
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
        try {
            accessController.getPlayerService().addFolder();
            Button button = new Button();
            String folderName = accessController.getPlayerService().getFolderName();
            button.setText(folderName);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setAlignment(CENTER_LEFT);
            button.setOnAction(_ -> setCurrentPlaylist(button.getText()));
            playlistVBox.getChildren().add(button);
            loadFolder();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void setCurrentPlaylist(String playlistName) {

        accessController.getPlayerService().setCurrentFolder(playlistName);
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
