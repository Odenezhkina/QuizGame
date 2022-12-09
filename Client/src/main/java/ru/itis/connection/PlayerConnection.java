package ru.itis.connection;

import ru.itis.models.Player;
import ru.itis.protocol.message.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

// todo fix
public class PlayerConnection implements Connection {
    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;
    private Player player;

    public PlayerConnection(InetAddress address, int port) {
        // todo generate id
        int id = (int) (Math.random() * 1000);
        try {
            socket = new Socket(address, port);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            receiveUserInformation();
            player.setUsername(player.getUsername() + "#" + id);
            player.setId(id);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void receiveUserInformation() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(in);
            player = (Player) inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public <T> void send(Message<T> message) throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(message);
        objOut.flush();
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public int getId() {
        return player.getId();
    }

    @Override
    public boolean isConnected() {
        return !socket.isClosed();
    }
}
