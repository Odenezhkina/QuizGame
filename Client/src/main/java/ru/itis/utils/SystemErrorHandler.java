package ru.itis.utils;

import javafx.scene.control.Alert;

public class SystemErrorHandler {
    public void handleError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null); // default title
        alert.setHeaderText(errorMessage);
        alert.showAndWait();
    }
}
