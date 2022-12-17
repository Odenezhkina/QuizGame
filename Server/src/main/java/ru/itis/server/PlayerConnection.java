package ru.itis.server;

import ru.itis.connection.Connection;
import ru.itis.models.Player;
import ru.itis.protocol.message.BasicMessage;

import java.io.*;
import java.net.Socket;

public class PlayerConnection implements Connection {
    private Server server;
    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;
    private Player player = Player.builder().build();

    public PlayerConnection(Socket socket, int id, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            out = socket.getOutputStream();
            in = socket.getInputStream();
            player.setId(id);
            ServerMessageListener listenerThread = new ServerMessageListener(in, server);
            listenerThread.start();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void send(BasicMessage message) throws IOException {
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
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException ignored) {
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
