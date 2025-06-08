package com.ui.controller.main_panel;

import com.repository.basicservice.interfaces.Playlist;
import com.repository.basicservice.interfaces.Track;
import com.ui.controller.AccessController;
import com.ui.controller.PlaylistItemController;
import com.ui.tools.FxmlFileOpener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

import static javafx.geometry.Pos.CENTER_LEFT;

public class MenuPanelController {
    @FXML
    public VBox playlistVBox;

    private AccessController accessController;

    public void initialize() {
        accessController = AccessController.getInstance();
        javafx.application.Platform.runLater(this::loadPlaylists);

    }

    private void loadPlaylists() {
        List<Playlist> playlistList = accessController.getMusicLibraryService().getAllPlaylists();
        for (Playlist playlist : playlistList) {
            loadPlaylistButton(playlist);
        }
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
            loadPlaylistButton(accessController.getMusicLibraryService().getActivePlaylist());
            loadFolder();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadPlaylistButton(Playlist playlist) {
        Button button = new Button();
        String folderName = playlist.getName();
        button.setText(folderName);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(CENTER_LEFT);
        button.setOnAction(_ -> setCurrentPlaylist(button.getText()));
        playlistVBox.getChildren().add(button);
    }

    public void setCurrentPlaylist(String playlistName) {
        FxmlFileOpener.loadFrame(accessController.getMainStackPane(), "playlist_panel.fxml");
        accessController.getMusicLibraryService().setActivePlaylist(playlistName);
        loadFolder();
    }

public void loadFolder() {
    try {
        accessController.getPlaylistPanelController().playlistNameLabel.setText(
            accessController.getMusicLibraryService().getActivePlaylist().getName()
        );
        List<Track> trackList = accessController.getMusicLibraryService().getActiveTrackList();
        accessController.getPlaylistPanelController().playlistTracksNumberLabel.setText("Tracks: " + trackList.size());
        
        ListView<Track> listView = accessController.getPlaylistPanelController().trackListView;
        listView.getItems().clear();
        listView.getItems().addAll(trackList);
        listView.setCellFactory(_ -> new ListCell<>() {
            private Node itemNode;
            private PlaylistItemController controller;

            @Override
            protected void updateItem(Track track, boolean empty) {
                super.updateItem(track, empty);
                
                if (empty || track == null) {
                    setGraphic(null);
                } else {
                    // Create the FXML loader and load the item only when needed
                    if (itemNode == null) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playlist_item.fxml"));
                            itemNode = loader.load();
                            controller = loader.getController();
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                            return;
                        }
                    }
                    
                    controller.loadTrackItem(
                        track.getName(),
                        track.getAlbum().getArtist().getName(),
                        track.getAlbum().getName(),
                        0,
                        track.getDurationSec()
                    );
                    controller.getPlayTrackButton().setOnAction(_ -> selectTrack(track.getName()));
                    setGraphic(itemNode);
                }
            }
        });
    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
}

    public void selectTrack(String trackName) {
        accessController.getMusicLibraryService().playTrack(trackName);
    }

}