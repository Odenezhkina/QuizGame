package com.example.client.controllers;

import com.example.client.utils.UiNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinRoomController {
    @FXML
    private Label joinRoomTitle;

    @FXML
    private TextField tfUsername;

    public JoinRoomController() {
    }

    @FXML
    protected void onJoinRoomButtonClick() {
        // todo logic about joining room
    }

    public void onBackButtonClick() {
        Stage currStage = (Stage) joinRoomTitle.getScene().getWindow();
        // Swap screen
        try {
            new UiNavigator().navigateToStartScreen(currStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
