package com.ui.controller;

import com.ui.tools.TrackPlayerHelper;
import javafx.scene.control.Button;

public class AccesController {
    private static String fileLocation;
    private static TrackPlayerHelper trackPlayerHelper;

    public static String getFileLocation() {
        return fileLocation;
    }

    public static void setFileLocation(String fileLocation) {
        AccesController.fileLocation = fileLocation;
    }

    public static TrackPlayerHelper getTrackPlayerHelper() {
        return trackPlayerHelper;
    }

    public static void setTrackPlayerHelper() {
        AccesController.trackPlayerHelper = new TrackPlayerHelper();
    }
}
