package com.example.client.controllers;

import com.example.client.utils.UiNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateRoomController {
    @FXML
    private Label labelCreateRoom;

    @FXML
    protected void onCreateRoomButtonClick() {
        // create room navigate to it
    }

    public void onBackButtonClick() {
        Stage currStage = (Stage) labelCreateRoom.getScene().getWindow();
        // Swap screen
        try {
            new UiNavigator().navigateToStartScreen(currStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
