package ru.itis.server.listeners;

import ru.itis.protocol.message.ContentMessage;
import ru.itis.server.PlayerConnection;
import ru.itis.server.Server;

public class NextQuestionListener implements ClientEventListener{
    private Server server;


    public void initServer(Server server){
        this.server = server;
    }

    @Override
    public void handMessage(PlayerConnection connection) {

    }
}
