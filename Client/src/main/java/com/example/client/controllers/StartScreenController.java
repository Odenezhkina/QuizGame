package com.example.client.controllers;

import com.example.client.utils.UiNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreenController {
    @FXML
    private Label labelQuizGame;

    @FXML
    protected void onJoinRoomButtonClick() {
        Stage currStage = (Stage) labelQuizGame.getScene().getWindow();
        try {
            new UiNavigator().navigateToScreen(currStage, "join-room.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onCreateRoomButtonClick(ActionEvent event) {
        Stage currStage = (Stage) labelQuizGame.getScene().getWindow();
        // Swap screen
        try {
            new UiNavigator().navigateToScreen(currStage, "create-room.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExitButtonClick() {
        Stage currStage = (Stage) labelQuizGame.getScene().getWindow();
        currStage.close();
    }

}
