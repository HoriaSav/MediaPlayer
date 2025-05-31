package com.ui.controller.main_panel;

import com.repository.basicservice.interfaces.Track;
import com.ui.controller.AccessController;
import com.ui.controller.PlaylistItemController;
import com.ui.tools.FxmlFileOpener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

import static javafx.geometry.Pos.CENTER_LEFT;

public class MenuPanelController {
    @FXML
    public VBox playlistVBox;

    private AccessController accessController;

    public void initialize() {
        accessController = AccessController.getInstance();
    }

    @FXML
    public void openExplorePanel() {
        FxmlFileOpener.loadFrame(accessController.getMainStackPane(), "explore_page.fxml");
    }

    @FXML
    public void openGenrePanel() {
        FxmlFileOpener.loadFrame(accessController.getMainStackPane(), "genre_panel.fxml");
    }

    @FXML
    public void openArtistPanel() {
        FxmlFileOpener.loadFrame(accessController.getMainStackPane(), "artists_panel.fxml");
    }

    @FXML
    public void openAlbumPanel() {
        FxmlFileOpener.loadFrame(accessController.getMainStackPane(), "albums_panel.fxml");
    }

    @FXML
    public void openSettingsPanel() {
        FxmlFileOpener.loadFrame(accessController.getMainStackPane(), "settings_panel.fxml");
    }

    @FXML
    public void addNewPlaylist() {
        try {
            accessController.getMusicLibraryService().addPlaylist();
            Button button = new Button();
            String folderName = accessController.getMusicLibraryService().getCurrentPlaylist().getName();
            button.setText(folderName);
            button.setMaxWidth(Double.MAX_VALUE);
            button.setAlignment(CENTER_LEFT);
            button.setOnAction(_ -> setCurrentPlaylist(button.getText()));
            playlistVBox.getChildren().add(button);
            loadFolder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentPlaylist(String playlistName) {
        FxmlFileOpener.loadFrame(accessController.getMainStackPane(), "playlist_panel.fxml");
        accessController.getMusicLibraryService().setCurrentPlaylist(playlistName);
        loadFolder();
    }

    public void loadFolder() {
        try {
            accessController.getAlbumPanelController().playlistNameLabel.setText(accessController.getMusicLibraryService().getCurrentPlaylist().getName());
            List<Track> trackList = accessController.getMusicLibraryService().getTracks();
            accessController.getAlbumPanelController().playlistTracksNumberLabel.setText("Tracks: " + trackList.size());
            accessController.getAlbumPanelController().trackListVBox.getChildren().clear();
            for (Track track : trackList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playlist_item.fxml"));
                Node itemNode = loader.load();
                PlaylistItemController playlistItemController = loader.getController();
                playlistItemController.loadTrackItem(track.getName(), track.getAlbum().getArtist().getName(), track.getAlbum().getName(), 0, track.getDurationSec());
                playlistItemController.getPlayTrackButton().setOnAction(_ -> selectTrack(track.getName()));
                accessController.getAlbumPanelController().trackListVBox.getChildren().add(itemNode);
            }
        } catch (IOException e) {
            //TODO exception handling
        }
    }

    public void selectTrack(String trackName) {
        accessController.getMusicLibraryService().playTrack(trackName);
    }

}
