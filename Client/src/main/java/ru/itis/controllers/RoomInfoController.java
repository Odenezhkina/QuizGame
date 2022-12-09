package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ru.itis.constants.Constants;
import ru.itis.utils.UiNavigator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomInfoController implements Initializable {
    @FXML
    private Label labelRoomNumber;

    @FXML
    private Label labelRoomMaxMembers;

    @FXML
    private Label labelRoomCreator;

    @FXML
    private ListView listViewActiveMembers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // todo init active members ??
//        listViewActiveMembers.setItems();
    }

    public void initRoomInfo(int roomNumber, int maxMembers, String creatorUsername) {
        labelRoomNumber.setText("Room №" + roomNumber);
        labelRoomMaxMembers.setText("Max members: " + maxMembers);
        labelRoomCreator.setText("Creator: " + creatorUsername);
        // todo init active members by roomNumber?
    }

    public void onStartQuizClick(ActionEvent event) throws IOException {
        // todo check if there are enough members
        int activeMembers = listViewActiveMembers.getItems().size();
        // start quiz or show warning
        if (activeMembers >= Constants.MIN_ROOM_MEMBER) {
            new UiNavigator().navigateToScreen(event, "quiz-screen.fxml");
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("There are not enough participants for the quiz");
            errorAlert.setContentText("The minimum member count for starting the quiz: " + Constants.MIN_ROOM_MEMBER);
            errorAlert.showAndWait();
        }
    }

    public void onLeaveRoomClick(ActionEvent event) {
        try {
            new UiNavigator().navigateToStartScreen(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
