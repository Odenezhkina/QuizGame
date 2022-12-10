package ru.itis.protocol.message;

import lombok.AllArgsConstructor;
import ru.itis.constants.MessageTypes;
import ru.itis.models.Player;

import java.util.List;

@AllArgsConstructor
public class GameOverMessage extends Message<List<Player>> {
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
