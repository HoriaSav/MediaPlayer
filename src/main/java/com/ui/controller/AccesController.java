package com.ui.controller;

import com.ui.tools.TrackPlayerHelper;
import javafx.scene.control.Slider;

public class AccesController {
    private static TrackPlayerHelper trackPlayerHelper;
    private static Slider progressSlider;

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
}
