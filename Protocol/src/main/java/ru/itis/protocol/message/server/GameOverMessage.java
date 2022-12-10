package ru.itis.protocol.message.server;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;
import ru.itis.models.Player;
import ru.itis.protocol.message.ContentMessage;

import java.util.List;

@AllArgsConstructor
public class GameOverMessage implements ContentMessage<List<Player>> {
    private final List<Player> content;
    private final int userId;

    @Override
    public MessageTypes getType() {
        return MessageTypes.GAME_OVER;
    }

    @Override
    public List<Player> getContent() {
        return content;
    }

    @Override
    public int getSenderId() {
        return userId;
    }

}
