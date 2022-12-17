package ru.itis.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ru.itis.connection.impl.ConnectionHolder;
import ru.itis.constants.RoomPreferences;
import ru.itis.models.Room;
import ru.itis.protocol.message.client.GetNewQuestionMessage;
import ru.itis.protocol.message.client.PlayerLeaveRoomMessage;
import ru.itis.utils.SystemErrorHandler;
import ru.itis.utils.exceptions.ConnectionNotInitializedException;
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

    private Room currentRoom;

    public void initRoomInfo(Room room) {
        currentRoom = room;
        labelRoomNumber.setText("Room №" + currentRoom.getId());
        labelRoomMaxMembers.setText("Max members: " + currentRoom.getCapacity());
        labelRoomCreator.setText("Creator: " + currentRoom.getCreatorUsername());
        initListView();
    }

    public void onStartQuizClick(ActionEvent event) throws IOException {
        int activeMembers = currentRoom.getCurrentSize();
        if (activeMembers < RoomPreferences.MIN_ROOM_MEMBER) {
            showErrorMessage("There are not enough participants for the quiz. " + "The minimum member count for starting the quiz: " + RoomPreferences.MIN_ROOM_MEMBER);
        } else {
            try {
                int roomId = ConnectionHolder.getConnection().getPlayer().getRoomId();

                // send player room NOT player id
                ConnectionHolder.getConnection().send(new GetNewQuestionMessage(roomId));
            } catch (ConnectionNotInitializedException e) {
                new SystemErrorHandler().handleError(e.getMessage());
            }
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
        } catch (IOException | ConnectionNotInitializedException e) {
            new SystemErrorHandler().handleError(e.getMessage());
        }
    }

    private void initListView() {
        listViewActiveMembers = new ListView<>();
        ObservableList<String> activePlayers = FXCollections.observableArrayList(
                currentRoom.getPlayers().values().
                        stream()
                        .map(it -> it.getUsername())
                        .collect(Collectors.toList()));
        listViewActiveMembers.setItems(activePlayers);
    }
}
