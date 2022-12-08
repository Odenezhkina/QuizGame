package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ru.itis.utils.UiNavigator;

import java.io.IOException;

public class RoomInfoController {
    @FXML
    private Label labelRoomNumber;

    @FXML
    private Label labelRoomMaxMembers;

    @FXML
    private Label labelRoomCreator;

    @FXML
    private ListView listViewActiveMembers;

    // from create room
    public void initRoomInfo(int roomNumber, int maxMembers, String creatorUsername) {
        labelRoomNumber.setText("Room №" + roomNumber);
        labelRoomMaxMembers.setText("Max members: " + maxMembers);
        labelRoomCreator.setText("Creator: " + creatorUsername);
        // todo init active members by roomNumber?
    }

    public void onStartQuizClick() {
        // check if there are enough members
        // start quiz or show warning
    }

    public void onLeaveRoomClick(ActionEvent event) {
        try {
            new UiNavigator().navigateToStartScreen(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
