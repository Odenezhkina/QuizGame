package ru.itis.messages;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;

@AllArgsConstructor
public class PlayerDisconnectMessage extends Message{
    private int senderId;

    @Override
    public MessageTypes getType() {
        return MessageTypes.PLAYER_DISCONNECT;
    }

    @Override
    public Object getContent() {
        return null;
    }

    @Override
    public int getSenderId() {
        return senderId;
    }
}
