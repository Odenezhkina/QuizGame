package ru.itis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(MainApplication
                        .class.getResource("start-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);

        String css = MainApplication.class.getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
////        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setTitle("QuizGame");
        stage.setScene(scene);
        stage.minHeightProperty().bind(scene.heightProperty());
        stage.minWidthProperty().bind(scene.widthProperty());
        stage.show();
    }
}
