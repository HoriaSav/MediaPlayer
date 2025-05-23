package com.ui.controller.main_panel;

import com.model.Track;
import com.ui.controller.AccessController;
import com.ui.controller.AlbumItemController;
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
    public void openArtistPanel() {
    }

    @FXML
    public void openAlbumPanel() {
    }

    @FXML
    public void openSettingsPanel() {
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
//            loadFolder();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void setCurrentPlaylist(String playlistName) {
        FxmlFileOpener.loadFrame(accessController.getMainStackPane(), "playlist_panel.fxml");
        accessController.getPlayerService().setCurrentFolder(playlistName);
        loadFolder();
    }

    public void loadFolder() {
        try {
            accessController.getAlbumPanelController().playlistNameLabel.setText(accessController.getPlayerService().getFolderName());
            List<Track> trackList = accessController.getPlayerService().getCurrentFolderTracks();
            accessController.getAlbumPanelController().playlistTracksNumberLabel.setText("Tracks: " + trackList.size());
            accessController.getAlbumPanelController().trackListVBox.getChildren().clear();
            for (Track track : trackList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/playlist_item.fxml"));
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

}
