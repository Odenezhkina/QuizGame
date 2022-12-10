package ru.itis.connection;

import ru.itis.models.Player;
import ru.itis.models.Room;
import ru.itis.protocol.message.*;
import ru.itis.utils.UiEventHandler;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class MessageListener extends Thread {
    private final ObjectInputStream in;
    private final UiEventHandler handler;

    public MessageListener(InputStream in, UiEventHandler handler) throws IOException {
        this.in = new ObjectInputStream(new BufferedInputStream(in));
        this.handler = handler;
    }

//    GAME_START // client -> server | content: -
//    GAME_OVER // server -> client | content: List<Player>

//    WAITING_FOR_PLAYERS, // server -> client | content: - (what if
//    we replace it with SYSTEM_MESSAGE with content like "Not enough players in the room")
//    WAITING_FOR_OTHER_PLAYERS, // server -> client | content: -
//    WAITING_FOR_ANSWERS, // ??
//    WAITING_FOR_NEXT_Q, // client -> server | content: waitingPlayerId (for notifying server that player is finished)
//    NEXT_QUESTION // server -> client | content: Question
//    RIGHT_QUESTION_ANSWER // client -> server | content: playerId, diffPoints?
//    TIME_IS_UP // server -> client | content: -

//    PLAYER_LEAVE_ROOM, // client -> server | content: playerId
//    ROOM_CREATE // server -> client | content: Room
//    PLAYER_JOIN_ROOM // client -> server | content: playerId, roomId
//    PLAYER_JOIN_ROOM_STATUS, // server -> client | content: Room

//    PLAYER_DISCONNECT,  // server -> client | content: -
//    PLAYER_ACCEPTED, // server -> client | content: -

//    SYSTEM_MESSAGE // server -> client | content: errorMessage

    @Override
    public void run() {
        Message message;
        try {
            while ((message = (Message) in.readObject()) != null) {
                switch (message.getType()) {
                    case GAME_START -> {
                        // ?? game start == quiz start?
                        handler.startGame();
                    }
                    case GAME_OVER -> {
                        List<Player> playerList = ((GameOverMessage) message).getContent();
                        handler.showStats(playerList);
                    }
                    case SYSTEM_MESSAGE -> {
                        // show like some alarm or what?
                        String serverMessage = ((ServerMessage) message).getContent();
                        handler.showSystemMessage(serverMessage);
                    }
                    case ROOM_CREATE -> {
                        Room createdRoom = ((CreateRoomMessage) message).getContent();
                        handler.roomCreated(createdRoom);
                    }
                    case PLAYER_JOIN_ROOM_STATUS -> {
                        Room room = ((JoinRoomStatusMessage) message).getContent();
                        handler.joinRoom(room);
                    }
                    case TIME_IS_UP -> handler.timeUp();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
