package ru.itis.server;

import ru.itis.messages.Message;
import ru.itis.utils.Connection;
import ru.itis.utils.Information;

import java.io.*;
import java.net.Socket;

public class PlayerConnection implements Connection {
    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;
    private Information userInformation;

    public PlayerConnection(Socket socket, int id){
        this.socket = socket;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
            receiveUserInformation();
            userInformation.setDescription(userInformation.getDescription() + "#" + id);
            userInformation.setId(id);
        }
        catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    private void receiveUserInformation() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(in);
            userInformation = (Information) inputStream.readObject();
        }
        catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void send(Message message) throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(message);
        objOut.flush();
    }

    @Override
    public Information getInformation() {
        return userInformation;
    }

    @Override
    public void close() {
        try {socket.close();}
        catch (IOException ignored) {}
    }

    @Override
    public int getId() {
        return userInformation.getId();
    }

    @Override
    public boolean isConnected() {
        return !socket.isClosed();
    }
}
