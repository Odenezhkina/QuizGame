package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ru.itis.utils.UiNavigator;

import java.io.IOException;

public class StartScreenController {
    @FXML
    private Label labelQuizGame;

    @FXML
    protected void navigateToJoinRoomScene(ActionEvent event) {
        try {
            new UiNavigator().navigateToScreen(event, "join-room.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void navigateToCreateRoomScene(ActionEvent event) {
        try {
            new UiNavigator().navigateToScreen(event, "create-room.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExitButtonClick(ActionEvent event) {
        try {
            new UiNavigator().exit(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
