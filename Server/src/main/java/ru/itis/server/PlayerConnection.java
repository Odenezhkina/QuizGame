package ru.itis.server;

import ru.itis.messages.Message;
import ru.itis.models.Player;

import java.io.*;
import java.net.Socket;

public class PlayerConnection implements Connection {
    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;
    private Player user;

    public PlayerConnection(Socket socket, int id){
        this.socket = socket;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
            receiveUserInformation();
            user.setUsername(user.getUsername() + "#" + id);
            user.setId(id);
        }
        catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    private void receiveUserInformation() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(in);
            user = (Player) inputStream.readObject();
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
    public Player getUser() {
        return user;
    }

    @Override
    public void close() {
        try {socket.close();}
        catch (IOException ignored) {}
    }

    @Override
    public int getId() {
        return user.getId();
    }

    @Override
    public boolean isConnected() {
        return !socket.isClosed();
    }
}
