package com.ui.controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AlbumPanelController {
    @FXML
    public Label fileLocationLabel;
    @FXML
    public Button getFileLocationButton;
    @FXML
    public VBox trackListVBox;

    public void initialize() {
        AccesController.setAlbumPanelController(this);
    }

    @FXML
    public void setFileLocation() {
        AccesController.getPlayerService().addFolder();
    }
}
