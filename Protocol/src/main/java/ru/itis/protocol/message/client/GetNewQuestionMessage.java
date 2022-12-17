package ru.itis.protocol.message.client;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;
import ru.itis.protocol.message.BasicMessage;

@AllArgsConstructor
public class GetNewQuestionMessage implements BasicMessage {
    private final int roomId;

    @Override
    public MessageTypes getType() {
        return MessageTypes.NEXT_QUESTION;
    }

    @Override
    public int getSenderId() {
        return roomId;
    }

}
