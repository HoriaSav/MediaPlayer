module MediaPlayer {
    requires javafx.fxml;
    requires javafx.controls;
    requires jdk.compiler;
    requires java.desktop;
    requires javafx.media;
    requires jaudiotagger;
    requires java.logging;
    requires java.sql;

    opens com to javafx.graphics, javafx.fxml;
    opens com.ui.controller to javafx.fxml;
    opens com.ui.tools to javafx.fxml;
    opens com.model to javafx.fxml;
    opens com.service to javafx.fxml;
    opens com.util to javafx.fxml;
}