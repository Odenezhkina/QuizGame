package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.itis.connection.impl.ConnectionHolder;
import ru.itis.utils.exceptions.NavigatorNotInitializedException;
import ru.itis.utils.navigation.UiNavigatorHolder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartScreenController implements Initializable {
    @FXML
    private Label labelQuizGame;

    @FXML
    private ImageView imageView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/img/ic_quiz.jpg"));
        imageView.setImage(image);
        centerImage();
    }

    @FXML
    protected void navigateToJoinRoomScene(ActionEvent event) {
        try {
            UiNavigatorHolder.getUiNavigator().navigateToScreen(event, "screens/join-room.fxml");
//            new UiNavigator(currentStage).navigateToScreen(event, "screens/join-room.fxml");
        } catch (IOException | NavigatorNotInitializedException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void navigateToCreateRoomScene(ActionEvent event) {
        try {
            UiNavigatorHolder.getUiNavigator().navigateToScreen(event, "screens/create-room.fxml");
//            new UiNavigator(currentStage).navigateToScreen(event, "screens/create-room.fxml");
        } catch (IOException | NavigatorNotInitializedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExitButtonClick(ActionEvent event) {
        try {
//            new UiNavigator(currentStage).exit(event);
            UiNavigatorHolder.getUiNavigator().navigateToStartScreen(event);
            ConnectionHolder.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void centerImage() {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }

}
