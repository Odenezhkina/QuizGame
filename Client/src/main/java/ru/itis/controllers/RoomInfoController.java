package ru.itis.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ru.itis.connection.ConnectionHolder;
import ru.itis.constants.RoomPreferences;
import ru.itis.models.Room;
import ru.itis.protocol.message.client.PlayerLeaveRoomMessage;
import ru.itis.protocol.message.client.StartGameMessage;
import ru.itis.utils.navigation.UiNavigator;

import java.io.IOException;
import java.util.stream.Collectors;

public class RoomInfoController {
    @FXML
    private Label labelRoomNumber;

    @FXML
    private Label labelRoomMaxMembers;

    @FXML
    private Label labelRoomCreator;

    @FXML
    private Label labelError;

    @FXML
    private ListView<String> listViewActiveMembers;


    public void initRoomInfo(Room room) {
        labelRoomNumber.setText("Room №" + room.getId());
        labelRoomMaxMembers.setText("Max members: " + room.getCapacity());
        labelRoomCreator.setText("Creator: " + room.getCreatorUsername());

        ObservableList<String> activePlayers = FXCollections.observableArrayList(room.getPlayers().values().stream().map(it -> it.getUsername()).collect(Collectors.toList()));
        listViewActiveMembers.setItems(activePlayers);
    }

    public void onStartQuizClick(ActionEvent event) throws IOException {
        int activeMembers = listViewActiveMembers.getItems().size();
        if (activeMembers < RoomPreferences.MIN_ROOM_MEMBER) {
            showErrorMessage("There are not enough participants for the quiz. " + "The minimum member count for starting the quiz: " + RoomPreferences.MIN_ROOM_MEMBER);
        } else {
            int playerId = ConnectionHolder.getConnection().getPlayer().getId();
            ConnectionHolder.getConnection().send(new StartGameMessage(playerId));
        }
    }

    private void showErrorMessage(String errorMessage) {
        labelError.setText(errorMessage);
    }

    public void onLeaveRoomClick(ActionEvent event) {
        try {
            int playerId = ConnectionHolder.getConnection().getPlayer().getId();
            ConnectionHolder.getConnection().send(new PlayerLeaveRoomMessage(playerId));
            new UiNavigator().navigateToStartScreen(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
