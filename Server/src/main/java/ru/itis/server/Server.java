package ru.itis.server;

import ru.itis.constants.Properties;
import ru.itis.messages.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private final HashMap<Integer, Connection> connections;

    private final ServerSocket serverSocket;

    private int userId = 1;

    public Server(){
        connections = new HashMap<>();
        try {
            serverSocket = new ServerSocket(Properties.port);
        }
        catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    public void start(){
        System.out.println("Server started");
        while (true){
            try {
                Socket socket = serverSocket.accept();
                addConnection(socket);
            }
            catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }
    public void addConnection(Socket socket){
        PlayerConnection connection = new PlayerConnection(socket,  userId++);
        connections.put(connection.getId(),connection);
        System.out.println("Connected user Id: " + (connection.getId() + "Nickname: " + connection.getUser().getUsername()));
    }

    private void sendToConnection(int connectionId, Message message){
        try {
            Connection con = connections.get(connectionId);
            if (con.isConnected()){
                con.send(message);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
