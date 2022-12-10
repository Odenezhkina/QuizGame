package ru.itis.protocol.message.server;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;
import ru.itis.models.Player;
import ru.itis.protocol.message.ContentMessage;

import java.util.Collection;

@AllArgsConstructor
public class GameOverMessage implements ContentMessage<Collection<Player>> {
    private final Collection<Player> content;
    private final int userId;

    @Override
    public MessageTypes getType() {
        return MessageTypes.GAME_OVER;
    }

    @Override
    public Collection<Player> getContent() {
        return content;
    }

    @Override
    public int getSenderId() {
        return userId;
    }

}
