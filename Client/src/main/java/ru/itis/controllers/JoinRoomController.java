package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.itis.connection.ConnectionHolder;
import ru.itis.protocol.message.JoinRoomMessage;
import ru.itis.utils.UiNavigator;

import java.io.IOException;

public class JoinRoomController {
    @FXML
    private TextField tfRoomCode;

    @FXML
    private TextField tfUsername;

    @FXML
    protected void joinRoom(ActionEvent event) {
        try {

            RoomInfoController controller = (RoomInfoController) new UiNavigator().navigateToScreen(event, "room-info.fxml");
            int playerId = ConnectionHolder.getConnection().getPlayer().getId();
            // send message
            ConnectionHolder.getConnection().send(new JoinRoomMessage(false, playerId));
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
