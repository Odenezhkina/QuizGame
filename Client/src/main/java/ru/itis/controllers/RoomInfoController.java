package ru.itis.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import ru.itis.connection.impl.ConnectionHolder;
import ru.itis.constants.RoomPreferences;
import ru.itis.models.Player;
import ru.itis.models.Room;
import ru.itis.protocol.message.client.GetNewQuestionMessage;
import ru.itis.protocol.message.client.PlayerLeaveRoomMessage;
import ru.itis.utils.SystemErrorHandler;
import ru.itis.utils.exceptions.ConnectionNotInitializedException;
import ru.itis.utils.exceptions.NavigatorNotInitializedException;
import ru.itis.utils.navigation.UiNavigatorHolder;

import java.io.IOException;
import java.util.stream.Collectors;

public class RoomInfoController {
    @FXML
    private ProgressIndicator bar;

    @FXML
    private Label labelRoomNumber;

    @FXML
    private Label labelRoomMaxMembers;

    @FXML
    private Label labelRoomCreator;

    @FXML
    private Label labelError;

    @FXML
    private ListView<String> listActiveMembers;

    private Room currentRoom;

    public void initRoomInfo(Room room) {
        currentRoom = room;
        labelRoomNumber.setText("Room №" + currentRoom.getId());
        labelRoomMaxMembers.setText("Max members: " + currentRoom.getCapacity());
        labelRoomCreator.setText("Creator: " + currentRoom.getCreatorUsername());
        initListView(room);
        bar.setVisible(false);
    }

    public void onStartQuizClick(ActionEvent event) throws IOException {
        int activeMembers = currentRoom.getCurrentSize();
        if (activeMembers < RoomPreferences.MIN_ROOM_MEMBER) {
            showErrorMessage("Not enough participants. " + "The minimum number is " + RoomPreferences.MIN_ROOM_MEMBER);
        } else {
            try {
                int roomId = ConnectionHolder.getConnection().getPlayer().getRoomId();

                bar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
                bar.setVisible(true);
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
            UiNavigatorHolder.getUiNavigator().navigateToStartScreen(event);
        } catch (IOException | ConnectionNotInitializedException | NavigatorNotInitializedException e) {
            new SystemErrorHandler().handleError(e.getMessage());
        }
    }

    private void initListView(Room room) {
        ObservableList<String> activePlayers = FXCollections.observableArrayList(
                room.getPlayers().values().
                        stream()
                        .map(Player::getUsername)
                        .collect(Collectors.toList()));
        listActiveMembers.setItems(activePlayers);

        //
        room.getPlayers().values().stream().forEach(System.out::println);
        //
    }
}
