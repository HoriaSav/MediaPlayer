module MediaPlayer {
    requires javafx.fxml;
    requires javafx.controls;
    requires jdk.compiler;
    requires java.desktop;
    requires javafx.media;
    requires jaudiotagger;
    requires java.logging;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires org.postgresql.jdbc;

    opens com to javafx.graphics, javafx.fxml;
    opens com.ui.controller to javafx.fxml;
    opens com.ui.tools to javafx.fxml;
    opens com.model to javafx.fxml;
    opens com.service to javafx.fxml;
    opens com.util to javafx.fxml;
    opens com.ui.controller.main_panel to javafx.fxml;

    exports com.repository.basicservice;
    exports com.repository.basicservice.interfaces;
    exports com.repository.exception;
    exports com.repository.schema;
    exports com.repository;
}