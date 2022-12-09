package ru.itis.controllers;

import ru.itis.connection.ConnectionHolder;
import ru.itis.constants.Constants;
import ru.itis.protocol.message.Message;
import ru.itis.utils.UiNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

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
            RoomInfoController controller = (RoomInfoController) new UiNavigator().navigateToScreen(event, "room-info.fxml");
            // todo get created room id
            controller.initRoomInfo(-1, Integer.parseInt(spinnerMaxMembers.getValue().toString()), tfUsername.getText());
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
