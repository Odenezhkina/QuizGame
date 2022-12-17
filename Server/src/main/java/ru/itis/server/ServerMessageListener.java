package ru.itis.server;

import ru.itis.protocol.message.ContentMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ServerMessageListener extends Thread{
    private final ObjectInputStream in;
    Server server;
    public ServerMessageListener(InputStream in, Server server) throws IOException {
        this.server = server;
        this.in = new ObjectInputStream(new BufferedInputStream(in));
    }

    @Override
    public void run() {
        ContentMessage<?> message;
        try {
            while ((message = (ContentMessage<?>) in.readObject()) != null) {
                server.handMessage(message);
            }
        }
        catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }
}
