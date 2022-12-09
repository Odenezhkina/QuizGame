package ru.itis.protocol.message;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;

@AllArgsConstructor
public class JoinRoomUserMessage extends Message<Boolean> {
    private final boolean roomId;
    private final boolean result;
    private final int senderId;

    @Override
    public MessageTypes getType() {
        return MessageTypes.PLAYER_JOIN_ROOM_STATUS;
    }

    @Override
    public Boolean getContent() {
        return result;
    }

    @Override
    public int getSenderId() {
        return senderId;
    }
}
