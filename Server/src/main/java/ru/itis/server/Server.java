package ru.itis.server;

import ru.itis.connection.Connection;
import ru.itis.constants.Properties;
import ru.itis.models.Room;
import ru.itis.protocol.message.ContentMessage;

import ru.itis.protocol.message.server.StatusMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private final HashMap<Integer, Connection> connections;

    private final ServerSocket serverSocket;

    private final HashMap<Integer, RoomService> rooms;

    private int userId = 1;

    private int roomId = 1;

    public Server() {
        connections = new HashMap<>();
        rooms = new HashMap<>();
        try {
            serverSocket = new ServerSocket(Properties.port);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void start() {
        System.out.println("Server started");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                addConnection(socket);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void addConnection(Socket socket) {
        PlayerConnection connection = new PlayerConnection(socket, userId++);
        connections.put(connection.getId(), connection);
        System.out.println("Connected user Id: " + (connection.getId() + "Nickname: " + connection.getPlayer().getUsername()));
    }

    private void sendToConnection(int connectionId, ContentMessage message) {
        try {
            Connection con = connections.get(connectionId);
            if (con.isConnected()) {
                con.send(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void handMessage(ContentMessage message) {
        switch (message.getType()) {
            case ROOM_CREATE -> createRoom((Room) message.getContent());
            case PLAYER_JOIN_ROOM -> joinRoom((Integer) message.getContent(), message.getSenderId());
            case PLAYER_LEAVE_ROOM -> leaveRoom(connections.get(message.getSenderId()).getPlayer().getRoomId(), message.getSenderId());
        }
    }

    public void removeConnection(int id) {
        Connection connection = connections.get(id);
        if (connection == null) {
            return;
        }
        RoomService roomService = rooms.get(connection.getPlayer().getRoomId());
        if(roomService != null) {
            roomService.removeConnection(id);
        }
        connections.remove(id);
    }
    private int createRoom(Room room){
        room.setId(roomId++);
        rooms.put(roomId,new RoomService(room.getId(), room.getName(), room.getCapacity(), room.getCurrentSize(), this));
        return room.getId();
    }
    public void joinRoom(int roomId, int senderId) {
        RoomService roomService = rooms.get(roomId);
        if (roomService.getGame() != null){
            sendToConnection(senderId, new StatusMessage("Game has already started", -1));
        }
        else if(roomService.getRoom().getCurrentSize() < roomService.getRoom().getCapacity()) {
            Connection connection = connections.get(senderId);
            roomService.addConnection(connection);
        }
        else {
            sendToConnection(senderId, new StatusMessage("Room is full", -1));
        }
    }
    public void leaveRoom(int roomId, int senderId) {
        RoomService roomService = rooms.get(roomId);
        roomService.removeConnection(senderId);
        roomService.sendToConnections(new StatusMessage("Player" + " " + senderId + " disconnect", -1));
    }

    public void removeRoom(int roomId) {
        rooms.remove(roomId);
    }
}
