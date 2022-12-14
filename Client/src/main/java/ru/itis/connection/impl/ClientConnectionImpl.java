package ru.itis.connection.impl;

import ru.itis.connection.ClientConnection;
import ru.itis.constants.ConnectionPreferences;
import ru.itis.models.Player;
import ru.itis.protocol.message.BasicMessage;
import ru.itis.protocol.message.client.PlayerAcceptedMessage;
import ru.itis.utils.exceptions.DisconnectedFromServerException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnectionImpl implements ClientConnection {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private Player player;

    public ClientConnectionImpl(String username) {
        try {
            socket = new Socket(ConnectionPreferences.host, ConnectionPreferences.port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            player = Player.builder().username(username).points(0).build();
            send(new PlayerAcceptedMessage(player.getId(), player.getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void send(BasicMessage message) throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(message);
        objOut.flush();
    }

    // todo object??
    @Override
    public Object receive() throws IOException, DisconnectedFromServerException, ClassNotFoundException {
        if (!isConnected()) {
            throw new DisconnectedFromServerException("Disconnected from server");
        }
        return in.readObject();
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayerName(String name) {
        player.setUsername(name);
    }

    public int getId() {
        return player.getId();
    }

    public boolean isConnected() {
        return !socket.isClosed();
    }

    @Override
    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }
}
