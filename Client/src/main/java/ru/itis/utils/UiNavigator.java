package ru.itis.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import ru.itis.MainApplication;

import java.io.IOException;

public class UiNavigator {


    public Object navigateToScreen(ActionEvent event, String resPathString) throws IOException {
        Stage currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(resPathString));
        Scene scene = new Scene(fxmlLoader.load(), currStage.getWidth(), currStage.getHeight());
        String css = MainApplication.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        currStage.setScene(scene);
        return fxmlLoader.getController();
    }

    public void navigateToStartScreen(ActionEvent event) throws IOException {
        navigateToScreen(event, "start-screen.fxml");
    }

    public void exit(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setContentText("Do you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Stage currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currStage.close();
        }
    }
}
