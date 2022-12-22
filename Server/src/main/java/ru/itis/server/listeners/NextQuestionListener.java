package ru.itis.server.listeners;

import ru.itis.protocol.message.client.GetNewQuestionMessage;
import ru.itis.server.PlayerConnection;
import ru.itis.server.Server;

public class NextQuestionListener implements ClientEventListener{
    private Server server;

    private GetNewQuestionMessage message;

    public NextQuestionListener(GetNewQuestionMessage message) {
        this.message = message;
    }

    public void initServer(Server server){
        this.server = server;
    }

    @Override
    public void handMessage(PlayerConnection connection) {
        server.nextQuestion(message.getSenderId());
    }
}
