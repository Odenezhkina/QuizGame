package ru.itis.server.listeners;

import ru.itis.constants.MessageTypes;

public class ServerEventListener {
    public static ClientEventListener getListener(MessageTypes type){
        switch (type) {
            case GAME_START -> {
                return new StartGameListener();
            }
            case ROOM_CREATE -> {
                return new CreateRoomListener();
            }
            case PLAYER_INIT_USERNAME -> {
                return new InitUsernameListener();
            }
            case PLAYER_JOIN_ROOM -> {
                return new JoinRoomListener();
            }
            case PLAYER_LEAVE_ROOM -> {
                return new LeaveRoomListener();
            }
        }
        return null;
    }
}
