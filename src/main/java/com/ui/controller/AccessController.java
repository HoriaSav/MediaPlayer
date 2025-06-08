package com.ui.controller;

import com.service.MusicLibraryService;
import com.service.MusicLibraryServiceImpl;
import com.ui.controller.container.TrackUiContainer;
import javafx.scene.layout.StackPane;


public class AccessController {
    private static volatile AccessController instance;
    private static StackPane mainPanel;
    private static MusicLibraryService musicLibraryService;
    private static TrackUiContainer trackUiContainer;
    private static PlaylistPanelController playlistPanelController;

    private AccessController() {
    }

    public static AccessController getInstance(){
        if (instance == null){
            synchronized (AccessController.class){
                if (instance == null){
                    instance = new AccessController();
                }
            }
        }
        return instance;
    }

    public void initializeStackPane(StackPane stackPane){
        if (mainPanel != null) {
            throw new IllegalStateException("Main panel already initialized");
        }
        mainPanel = stackPane;
    }

    public StackPane getMainStackPane(){
        return mainPanel;
    }

    public MusicLibraryService getMusicLibraryService() {
        return musicLibraryService;
    }

    public void setMusicLibraryService() {
        AccessController.musicLibraryService = new MusicLibraryServiceImpl();
    }

    public TrackUiContainer getTrackUiContainer() {
        return trackUiContainer;
    }

    public void setTrackUiContainer(TrackUiContainer trackUiContainer) {
        AccessController.trackUiContainer = trackUiContainer;
    }

    public void setAlbumPanelController(PlaylistPanelController playlistPanelController) {
        AccessController.playlistPanelController = playlistPanelController;
    }

    public PlaylistPanelController getPlaylistPanelController() {
        return playlistPanelController;
    }
}
