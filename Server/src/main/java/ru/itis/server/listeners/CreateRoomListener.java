package ru.itis.server.listeners;

import ru.itis.protocol.message.ContentMessage;
import ru.itis.server.PlayerConnection;
import ru.itis.server.Server;

public class CreateRoomListener implements ClientEventListener{
    private Server server;
    @Override
    public void initServer(Server server){
        this.server = server;
    }
    @Override
    public void handMessage(PlayerConnection connection, ContentMessage<?> message) {
        server.createRoom(connection.getId(), (int) message.getContent());
    }
}
