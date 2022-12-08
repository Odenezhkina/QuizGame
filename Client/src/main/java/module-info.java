module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.client to javafx.fxml;
    exports com.example.client;
    exports com.example.client.controllers;
    opens com.example.client.controllers to javafx.fxml;
    exports com.example.client.utils;
    opens com.example.client.utils to javafx.fxml;
}
