package ru.itis.connection;

import ru.itis.constants.ConnectionPreferences;
import ru.itis.models.Player;
import ru.itis.protocol.message.BasicMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final InputStream in;
    private final OutputStream out;
    private Player player;

    public Client() {
        try {
            socket = new Socket(ConnectionPreferences.host, ConnectionPreferences.port);
            out = socket.getOutputStream();
            in = socket.getInputStream();
//            receiveUserInformation();
//            int id = (int) (Math.random() * 1000);
//            player.setUsername(player.getUsername() + "#" + id);
//            player.setId(id);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

//    private void receiveUserInformation() {
//        try {
//            ObjectInputStream inputStream = new ObjectInputStream(in);
//            player = (Player) inputStream.readObject();
//        } catch (ClassNotFoundException | IOException e) {
//            throw new RuntimeException(e.getMessage());
//        }
//    }


    public void send(BasicMessage message) throws IOException {
        ObjectOutputStream objOut = new ObjectOutputStream(out);
        objOut.writeObject(message);
        objOut.flush();
    }


    public Player getPlayer() {
        return player;
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ignored) {
        }
    }


    public int getId() {
        return player.getId();
    }

    public boolean isConnected() {
        return !socket.isClosed();
    }

    public InputStream getInputStream() {
        return in;
    }
}
