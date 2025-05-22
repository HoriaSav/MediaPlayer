package com.ui.controller;

import com.ui.tools.FxmlFileOpener;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class MainPanelController {

    @FXML
    public StackPane stackPane;

    private AccessController accessController;

    public void initialize() {
        accessController = AccessController.getInstance();
        accessController.setTrackPlayerHelper();
        FxmlFileOpener.loadFrame(stackPane, "album_panel.fxml");
    }
}
