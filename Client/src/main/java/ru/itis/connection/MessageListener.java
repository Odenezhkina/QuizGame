package ru.itis.connection;

import ru.itis.models.Player;
import ru.itis.models.Question;
import ru.itis.models.Room;
import ru.itis.protocol.message.ContentMessage;
import ru.itis.protocol.message.server.*;
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

//    DONE GAME_START // client -> server | content: - or roomId
//    GAME_OVER // server -> client | content: List<Player>

//    WAITING_FOR_PLAYERS, // server -> client | content: - (what if
//    we replace it with SYSTEM_MESSAGE with content like "Not enough players in the room")
//    WAITING_FOR_OTHER_PLAYERS, // server -> client | content: -
//    WAITING_FOR_ANSWERS, // ??
//    WAITING_FOR_NEXT_Q, // client -> server | content: waitingPlayerId (for notifying server that player is finished)
//    DONE NEXT_QUESTION // server -> client | content: Question
//    DONE RIGHT_QUESTION_ANSWER // client -> server | content: playerId, diffPoints?
//    DONE TIME_IS_UP // server -> client | content: -

//    DONE PLAYER_LEAVE_ROOM, // client -> server | content: playerId
//    DONE ROOM_CREATE // server -> client | content: Room
//    DONE PLAYER_JOIN_ROOM // client -> server | content: playerId, roomId
//    DONE PLAYER_JOIN_ROOM_STATUS, // server -> client | content: Room

//    PLAYER_DISCONNECT,  // server -> client | content: -
//    PLAYER_ACCEPTED, // server -> client | content: -

//    DONE SYSTEM_MESSAGE // server -> client | content: errorMessage

    @Override
    public void run() {
        ContentMessage message;
        try {
            while ((message = (ContentMessage) in.readObject()) != null) {
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
                        handler.roomCreated(createdRoom);
                    }
                    case PLAYER_JOIN_ROOM_STATUS -> {
                        Room room = ((JoinRoomStatusMessage) message).getContent();
                        handler.joinRoom(room);
                    }
                    case TIME_IS_UP -> handler.timeUp();
                    case NEXT_QUESTION -> {
                        Question question = ((NextQuestionMessage) message).getContent();
                        handler.showNextQuestion(question);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
