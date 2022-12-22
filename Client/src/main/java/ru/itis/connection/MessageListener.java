package ru.itis.connection;

import ru.itis.connection.impl.ConnectionHolder;
import ru.itis.models.Player;
import ru.itis.models.Question;
import ru.itis.models.Room;
import ru.itis.protocol.message.ContentMessage;
import ru.itis.protocol.message.server.*;
import ru.itis.utils.UiEventHandler;
import ru.itis.utils.exceptions.ConnectionNotInitializedException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class MessageListener extends Thread {
    private final ObjectInputStream in;
    private final UiEventHandler handler;

    public MessageListener(InputStream in) throws IOException {
        this.handler = new UiEventHandler();
        this.in = new ObjectInputStream(new BufferedInputStream(in));
    }

    @Override
    public void run() {
        ContentMessage<?> message;
        try {
            while ((message = (ContentMessage<?>) in.readObject()) != null) {
                switch (message.getType()) {
                    case GAME_OVER -> {
                        List<Player> playerList = ((GameOverMessage) message).getContent();
                        handler.showStats(playerList);
                    }
                    case SYSTEM_MESSAGE -> {
                        String serverMessage = ((SystemMessage) message).getContent();
                        handler.showSystemMessage(serverMessage);
                    }
                    case ROOM_CREATE_STATUS -> {
                        Room createdRoom = ((CreateRoomStatusMessage) message).getContent();
                        initPlayerRoom(createdRoom.getId());
                        handler.roomCreated(createdRoom);
                    }
                    case TIME_IS_UP -> handler.timeUp();
                    case NEXT_QUESTION -> {
                        Question question = ((NextQuestionMessage) message).getContent();
                        handler.showNextQuestion(question);
                    }
                    case UPDATE_ROOM -> { //case PLAYER_JOIN_ROOM_STATUS
                        Room room = ((RoomWasUpdatedMessage) message).getContent();
                        initPlayerRoom(room.getId());
                        handler.updateRoom(room);
                    }
                    case PLAYER_ACCEPTED -> {
                        Player player = ((PlayerAcceptedStatusMessage) message).getContent();
                        // initializing default player
                        ConnectionHolder.getConnection().setPlayer(player);
                    }
                    default -> handler.showSystemMessage("Unknown message from server");
                }
            }
        } catch (ClassNotFoundException | ConnectionNotInitializedException | IOException e) {
            e.printStackTrace();
        }
    }

    private void initPlayerRoom(int roomId) throws ConnectionNotInitializedException {
        ConnectionHolder.getConnection().getPlayer().setRoomId(roomId);
    }

}
