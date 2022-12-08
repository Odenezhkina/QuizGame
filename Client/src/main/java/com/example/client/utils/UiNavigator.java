package com.example.client.utils;

import com.example.client.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UiNavigator {

    public void navigateToScreen(Stage currStage, String resPathString) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(resPathString));
        Scene scene = new Scene(fxmlLoader.load(), currStage.getWidth(), currStage.getHeight());
        currStage.setScene(scene);
    }

    public void navigateToStartScreen(Stage stage) throws IOException {
        navigateToScreen(stage, "start-screen.fxml");
    }
}
