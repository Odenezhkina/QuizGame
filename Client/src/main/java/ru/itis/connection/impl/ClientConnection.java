package ru.itis.connection.impl;

import ru.itis.connection.MessageListener;
import ru.itis.constants.ConnectionPreferences;
import ru.itis.models.Player;
import ru.itis.protocol.message.BasicMessage;
import ru.itis.utils.exceptions.DisconnectedFromServerException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientConnection {
    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;
    private Player player;

    public ClientConnection() {
        try {
            socket = new Socket(InetAddress.getByName(ConnectionPreferences.host), ConnectionPreferences.port);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            MessageListener messageListenerThread = new MessageListener(in);
            messageListenerThread.start();
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void send(BasicMessage message) throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(message);
        objOut.flush();
    }

    public Object receive() throws IOException, DisconnectedFromServerException, ClassNotFoundException {
        if (!isConnected()) {
            throw new DisconnectedFromServerException("Disconnected from server");
        }
        ObjectInputStream objIn = new ObjectInputStream(in);
        return objIn.readObject();
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getId() {
        return player.getId();
    }

    public boolean isConnected() {
        return !socket.isClosed();
    }

    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }
}
