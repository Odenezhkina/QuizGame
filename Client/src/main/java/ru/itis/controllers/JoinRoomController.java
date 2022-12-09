package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.itis.utils.UiNavigator;

import java.io.IOException;

public class JoinRoomController {
    @FXML
    private TextField tfRoomCode;

    @FXML
    private TextField tfUsername;

    public JoinRoomController() {
    }

    @FXML
    protected void joinRoom(ActionEvent event) {
        RoomInfoController controller = null;
        try {
            controller = (RoomInfoController) new UiNavigator().navigateToScreen(event, "room-info.fxml");
            // todo validate roomCode + get joined + room max members
            controller.initRoomInfo(Integer.parseInt(tfRoomCode.getText()), -1, tfUsername.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onBackButtonClick(ActionEvent event) {
        try {
            new UiNavigator().navigateToStartScreen(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
