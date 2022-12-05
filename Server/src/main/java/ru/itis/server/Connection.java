package ru.itis.server;

import ru.itis.messages.Message;
import ru.itis.models.Player;

import java.io.IOException;

public interface Connection {

    void send(Message message) throws IOException;

    Player getUser();

    void close();

    int getId();

    boolean isConnected();
}
