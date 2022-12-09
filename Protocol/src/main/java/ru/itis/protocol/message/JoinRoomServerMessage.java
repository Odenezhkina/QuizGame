package ru.itis.protocol.message;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;
import ru.itis.models.Room;

@AllArgsConstructor
public class JoinRoomServerMessage extends Message<Room>{
    private final Room room;
    private final int senderId;

    @Override
    public MessageTypes getType() {
        return MessageTypes.PLAYER_JOIN_ROOM_STATUS;
    }

    @Override
    public Room getContent() {
        return room;
    }

    @Override
    public int getSenderId() {
        return senderId;
    }
}
