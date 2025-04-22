package com.ui.controller;

import com.service.PlayerService;
import com.ui.controller.container.TrackUiContainer;


public class AccesController {
    private static PlayerService playerService;
    private static TrackUiContainer trackUiContainer;
    private static AlbumPanelController albumPanelController;

    public static PlayerService getPlayerService() {
        return playerService;
    }

    public static void setTrackPlayerHelper() {
        AccesController.playerService = new PlayerService();
    }

    public static TrackUiContainer getTrackUiContainer() {
        return trackUiContainer;
    }

    public static void setTrackUiContainer(TrackUiContainer trackUiContainer) {
        AccesController.trackUiContainer = trackUiContainer;
    }

    public static void setAlbumPanelController(AlbumPanelController albumPanelController) {
        AccesController.albumPanelController = albumPanelController;
    }

    public static AlbumPanelController getAlbumPanelController() {
        return albumPanelController;
    }
}
