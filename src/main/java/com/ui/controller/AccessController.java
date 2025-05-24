package com.ui.controller;

import com.service.PlayerService;
import com.ui.controller.container.TrackUiContainer;


public class AccessController {
    private static volatile AccessController instance;
    private static PlayerService playerService;
    private static TrackUiContainer trackUiContainer;
    private static AlbumPanelController albumPanelController;

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

    public void setAlbumPanelController(AlbumPanelController albumPanelController) {
        AccessController.albumPanelController = albumPanelController;
    }

    public AlbumPanelController getAlbumPanelController() {
        return albumPanelController;
    }
}
