package ru.itis.connection;

import ru.itis.models.Player;
import ru.itis.protocol.message.Message;

import java.io.IOException;

public interface Connection {

    <T> void send(Message<T> message) throws IOException;

    <T> Message<T> receive() throws IOException;

    Player getPlayer();

    void close();

    int getId();

    boolean isConnected();
}
