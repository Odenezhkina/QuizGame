package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import ru.itis.connection.ConnectionHolder;
import ru.itis.constants.Constants;
import ru.itis.protocol.message.client.CreateRoomMessage;
import ru.itis.utils.navigation.UiNavigator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateRoomController implements Initializable {
    @FXML
    private Label labelCreateRoom;

    @FXML
    private TextField tfUsername;

    @FXML
    private Spinner<Integer> spinnerMaxMembers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(Constants.MIN_ROOM_MEMBER, Constants.MAX_ROOM_MEMBER);
        valueFactory.setValue(Constants.MIN_ROOM_MEMBER);
        spinnerMaxMembers.setValueFactory(valueFactory);
    }

    @FXML
    protected void createRoom(ActionEvent event) {
        try {
            int playerId = ConnectionHolder.getConnection().getId();
            ConnectionHolder.getConnection().send(new CreateRoomMessage(spinnerMaxMembers.getValue(), playerId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateBack(ActionEvent event) {
        try {
            new UiNavigator().navigateToStartScreen(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
