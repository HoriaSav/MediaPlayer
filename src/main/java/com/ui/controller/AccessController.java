package com.ui.controller;

import com.service.PlayerService;
import com.ui.controller.container.TrackUiContainer;
import javafx.scene.layout.StackPane;


public class AccessController {
    private static volatile AccessController instance;
    private static StackPane mainPanel;
    private static PlayerService playerService;
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

    public PlayerService getPlayerService() {
        return playerService;
    }

    public void setTrackPlayerHelper() {
        AccessController.playerService = new PlayerService();
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

    public PlaylistPanelController getAlbumPanelController() {
        return playlistPanelController;
    }
}
