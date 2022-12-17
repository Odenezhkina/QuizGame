package ru.itis.constants;

public enum MessageTypes {
    GAME_START,
    GAME_OVER,

    WAITING_FOR_PLAYERS,
    WAITING_FOR_OTHER_PLAYERS,
    WAITING_FOR_ANSWERS,

    PLAYER_ACCEPTED,
    PLAYER_INIT_USERNAME,
    PLAYER_DISCONNECT,

    PLAYER_LEAVE_ROOM,
    PLAYER_LEAVE_ROOM_STATUS,
    PLAYER_JOIN_ROOM,
    PLAYER_JOIN_ROOM_STATUS,
    ROOM_CREATE,
    ROOM_CREATE_STATUS,

    TIME_IS_UP,
    SYSTEM_MESSAGE,

    NEXT_QUESTION,
    RIGHT_ANSWER,
    UPDATE_ROOM
}
