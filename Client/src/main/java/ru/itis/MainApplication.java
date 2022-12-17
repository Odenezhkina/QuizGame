package ru.itis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itis.connection.impl.ConnectionHolder;

import java.io.IOException;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(MainApplication
                        .class.getResource("screens/start-screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);

        String css = MainApplication.class.getResource("screens/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("QuizGame");
        stage.setScene(scene);
        stage.minHeightProperty().bind(scene.heightProperty());
        stage.minWidthProperty().bind(scene.widthProperty());
        stage.show();

        ConnectionHolder.initializeConnection();
    }

    @Override
    public void stop() {
        try {
            // if we close window we also close current connection
            ConnectionHolder.closeConnection();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
