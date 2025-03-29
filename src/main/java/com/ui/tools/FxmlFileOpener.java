package com.ui.tools;

import com.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.function.Consumer;

public class FxmlFileOpener {

    public FxmlFileOpener() {
    }

    public void openFileOnAction(String filename) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(filename));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);

            Scene scene = new Scene(fxmlLoader.load(), 900, 700);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadFrame(StackPane stackPane, String filename) {
        try {
            FXMLLoader loader = new FXMLLoader(FxmlFileOpener.class.getResource("/fxml/" + filename));

            Parent page = loader.load();  // JavaFX will automatically use the fx:controller from the FXML
            stackPane.getChildren().setAll(page);  // Replace content in StackPane

        } catch (IOException e) {
            System.out.println("Error loading FXML: " + filename);
            e.printStackTrace();
        }
    }

    public static <T> void addCustomizedFXMLTo(Pane parent, String filename, Consumer<T> controllerModifier) {
        try {
            FXMLLoader loader = new FXMLLoader(FxmlFileOpener.class.getResource("/fxml/" + filename));
            Parent node = loader.load();

            // Get controller
            T controller = loader.getController();

            // Customize this instance
            controllerModifier.accept(controller);

            // Add to UI
            parent.getChildren().add(node);

        } catch (IOException e) {
            System.out.println("Error loading FXML: " + filename);
            e.printStackTrace();
        }
    }
}