package com.ui.controller;

import com.ui.controller.container.TrackUiContainer;
import com.ui.tools.TrackPlayerHelper;
import javafx.scene.control.Slider;

public class AccesController {
    private static TrackPlayerHelper trackPlayerHelper;
    private static Slider progressSlider;
    private static TrackUiContainer trackUiContainer;
    private static MainPanelController mainPanelController;

    public static TrackPlayerHelper getTrackPlayerHelper() {
        return trackPlayerHelper;
    }

    public static void setTrackPlayerHelper() {
        AccesController.trackPlayerHelper = new TrackPlayerHelper();
    }

    public static Slider getProgressSlider() {
        return progressSlider;
    }

    public static void setProgressSlider() {
        AccesController.progressSlider = new Slider();
    }

    public static TrackUiContainer getTrackUiContainer() {
        if(trackUiContainer == null) {
            setTrackUiContainer(mainPanelController.getTrackUiContainer());
        }
        return trackUiContainer;
    }

    public static void setTrackUiContainer(TrackUiContainer trackUiContainer) {
        AccesController.trackUiContainer = trackUiContainer;
    }

    public static MainPanelController getMainPanelController() {
        return mainPanelController;
    }

    public static void setMainPanelController(MainPanelController mainPanelController) {
        AccesController.mainPanelController = mainPanelController;
    }
}
