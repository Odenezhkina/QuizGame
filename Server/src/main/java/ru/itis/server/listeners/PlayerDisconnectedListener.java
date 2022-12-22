package ru.itis.server.listeners;

import ru.itis.protocol.message.client.PlayerDisconnectedMessage;
import ru.itis.server.PlayerConnection;
import ru.itis.server.Server;

public class PlayerDisconnectedListener implements ClientEventListener {
    Server server;
    private PlayerDisconnectedMessage message;
    public PlayerDisconnectedListener(PlayerDisconnectedMessage message) {
        this.message = message;
    }

    @Override
    public void handMessage(PlayerConnection connection) {
        server.removeConnection(connection.getId());
    }

    @Override
    public void initServer(Server server) {
        this.server = server;
    }
}
