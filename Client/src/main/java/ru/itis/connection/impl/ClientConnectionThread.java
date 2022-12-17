package ru.itis.connection.impl;

import ru.itis.connection.MessageListenerThread;
import ru.itis.constants.ConnectionPreferences;
import ru.itis.models.Player;
import ru.itis.protocol.message.BasicMessage;
import ru.itis.utils.exceptions.DisconnectedFromServerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnectionThread {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private Player player;

    public ClientConnectionThread() {
        try {
            socket = new Socket(ConnectionPreferences.host, ConnectionPreferences.port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            // if server receives socket created earlier
            // we have to wait until PlayerAcceptedStatusMessage from server init default player
            MessageListenerThread messageListenerThread = new MessageListenerThread(in);
            messageListenerThread.start();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
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
        return in.readObject();
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
