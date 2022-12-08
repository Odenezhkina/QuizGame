package com.example.client.controllers;

import com.example.client.utils.UiNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomInfoController {
    @FXML
    private Label labelRoomInfo;

    @FXML
    private ListView listViewActiveMembers;


    public void onStartQuizClick() {
        // check if there are enough members
        // start quiz or show warning
    }

    public void onLeaveRoomClick() {
        Stage curStage = (Stage) labelRoomInfo.getScene().getWindow();
        try {
            new UiNavigator().navigateToStartScreen(curStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
