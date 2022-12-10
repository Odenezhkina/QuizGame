package ru.itis.protocol.message.server;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;
import ru.itis.protocol.message.ContentMessage;

@AllArgsConstructor
public class StatusMessage implements ContentMessage<String> {
    private final String string;
    private final int senderId;

    @Override
    public MessageTypes getType() {
        return MessageTypes.PLAYER_JOIN_ROOM_STATUS;
    }

    @Override
    public String getContent() {
        return string;
    }

    @Override
    public int getSenderId() {
        return senderId;
    }
}
