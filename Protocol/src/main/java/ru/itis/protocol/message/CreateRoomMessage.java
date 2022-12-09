package ru.itis.protocol.message;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;
import ru.itis.models.Room;

@AllArgsConstructor
public class CreateRoomMessage extends Message<Room>{
    private final Room content;
    private final int userId;

    @Override
    public MessageTypes getType() {
        return MessageTypes.ROOM_CREATE;
    }

    @Override
    public Room getContent() {
        return content;
    }

    @Override
    public int getSenderId() {
        return userId;
    }

}
