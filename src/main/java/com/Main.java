package com;

import javafx.application.Application;
import javafx.stage.Stage;
import com.ui.tools.FxmlFileOpener;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        disableLogging();
        FxmlFileOpener fxmlFileOpener = new FxmlFileOpener();
        fxmlFileOpener.openFileOnAction("/fxml/main_panel.fxml");
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void disableLogging() {
        LogManager.getLogManager().reset();
        Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);
    }
}
