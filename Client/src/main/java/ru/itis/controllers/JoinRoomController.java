package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.itis.connection.ConnectionHolder;
import ru.itis.protocol.message.JoinRoomUserMessage;
import ru.itis.protocol.message.Message;
import ru.itis.utils.UiNavigator;

import java.io.IOException;

public class JoinRoomController {
    @FXML
    private TextField tfRoomCode;

    @FXML
    private TextField tfUsername;

    @FXML
    protected void joinRoom(ActionEvent event) {
        RoomInfoController controller = null;
        try {
            controller = (RoomInfoController) new UiNavigator().navigateToScreen(event, "room-info.fxml");
            controller.initRoomInfo(Integer.parseInt(tfRoomCode.getText()), -1, tfUsername.getText());
            // todo validate roomCode + get joined + room max members
            ConnectionHolder.getConnection().send(new JoinRoomUserMessage());
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
