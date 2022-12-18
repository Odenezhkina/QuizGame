package ru.itis.server.listeners;

import ru.itis.protocol.message.ContentMessage;
import ru.itis.server.PlayerConnection;
import ru.itis.server.Server;

public interface ClientEventListener {
    void handMessage(PlayerConnection connection, ContentMessage<?> message);
    void initServer(Server server);
}
