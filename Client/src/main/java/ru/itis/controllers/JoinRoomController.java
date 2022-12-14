package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.itis.connection.impl.ConnectionHolder;
import ru.itis.protocol.message.client.JoinRoomMessage;
import ru.itis.utils.SystemErrorHandler;
import ru.itis.utils.exceptions.ConnectionNotInitializedException;
import ru.itis.utils.navigation.UiNavigator;

import java.io.IOException;

public class JoinRoomController {
    @FXML
    private TextField tfRoomCode;

    @FXML
    private Label labelRoomCodeError;

    @FXML
    private TextField tfUsername;

    @FXML
    protected void joinRoom(ActionEvent event) {
        try {
            int roomCode = Integer.parseInt(tfRoomCode.getText());
            int playerId = ConnectionHolder.getConnection().getId();
            ConnectionHolder.getConnection().setPlayerName(tfUsername.getText());
            ConnectionHolder.getConnection().send(new JoinRoomMessage(playerId, roomCode));
        } catch (NumberFormatException ex) {
            labelRoomCodeError.setVisible(true);
        } catch (IOException | ConnectionNotInitializedException e) {
            new SystemErrorHandler().handleError(e.getMessage());
        }
    }

    public void onBackButtonClick(ActionEvent event) {
        try {
            new UiNavigator().navigateToStartScreen(event);
        } catch (IOException e) {
            new SystemErrorHandler().handleError(e.getMessage());
        }
    }
}
