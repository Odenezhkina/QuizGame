package ru.itis.connection;

import ru.itis.protocol.message.BasicMessage;
import ru.itis.utils.exceptions.DisconnectedFromServerException;

import java.io.IOException;

public interface ClientConnection extends AutoCloseable {
    void send(BasicMessage message) throws IOException;

    Object receive() throws IOException, DisconnectedFromServerException, ClassNotFoundException;
}
