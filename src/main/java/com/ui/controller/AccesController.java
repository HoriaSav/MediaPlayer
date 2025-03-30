package com.ui.controller;

import com.ui.controller.container.TrackUiContainer;
import com.ui.tools.TrackPlayerHelper;
import javafx.scene.control.Slider;

public class AccesController {
    private static TrackPlayerHelper trackPlayerHelper;
    private static TrackUiContainer trackUiContainer;

    public static TrackPlayerHelper getTrackPlayerHelper() {
        return trackPlayerHelper;
    }

    public static void setTrackPlayerHelper() {
        AccesController.trackPlayerHelper = new TrackPlayerHelper();
    }

    public static TrackUiContainer getTrackUiContainer() {
        return trackUiContainer;
    }

    public static void setTrackUiContainer(TrackUiContainer trackUiContainer) {
        AccesController.trackUiContainer = trackUiContainer;
    }
}
