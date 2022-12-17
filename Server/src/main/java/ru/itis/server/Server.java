package ru.itis.server;

import ru.itis.connection.Connection;
import ru.itis.constants.ConnectionPreferences;
import ru.itis.models.Room;
import ru.itis.protocol.message.ContentMessage;

import ru.itis.protocol.message.server.PlayerAcceptedStatusMessage;
import ru.itis.protocol.message.server.SystemMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {
    private final HashMap<Integer, Connection> connections;
    private final HashMap<Integer, ServerMessageListener> listeners;

    private final ServerSocket serverSocket;

    private final HashMap<Integer, RoomService> rooms;

    private int userId = 1;

    private int roomId = 1;

    public Server() {
        connections = new HashMap<>();
        listeners = new HashMap<>();
        rooms = new HashMap<>();
        try {
            serverSocket = new ServerSocket(ConnectionPreferences.port);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void start() {
        System.out.println("Server started");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("add connection");
                addConnection(socket);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void addConnection(Socket socket) {
        PlayerConnection connection = new PlayerConnection(socket, userId++, this);
        connections.put(connection.getId(), connection);
        sendToConnection(connection.getId(), new PlayerAcceptedStatusMessage(connection.getId(), connection.getPlayer()));
        System.out.println("User: " + connection.getId());
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
            case GAME_START -> rooms.get(connections.get(message.getSenderId()).getPlayer().getRoomId()).startGame();
            case ROOM_CREATE -> createRoom((Room) message.getContent());
            case PLAYER_JOIN_ROOM -> joinRoom((Integer) message.getContent(), message.getSenderId());
            case PLAYER_LEAVE_ROOM -> leaveRoom(connections.get(message.getSenderId()).getPlayer().getRoomId(), message.getSenderId());
            case PLAYER_DISCONNECT -> removeConnection(message.getSenderId());
        }
    }

    public void removeConnection(int id) {
        Connection connection = connections.get(id);
        if (connection == null) {
            return;
        }
        RoomService roomService = rooms.get(connection.getPlayer().getRoomId());
        if (roomService != null) {
            roomService.removeConnection(id);
        }
        listeners.get(id).interrupt();
        listeners.remove(id);
        connections.remove(id);
        connection.close();
    }
    private int createRoom(Room room){
        room.setId(roomId++);
        rooms.put(roomId,new RoomService(room.getId(), room.getName(), room.getCapacity(), room.getCurrentSize(), this));
        return room.getId();
    }
    public void joinRoom(int roomId, int senderId) {
        RoomService roomService = rooms.get(roomId);
        if (roomService.getGame() != null){
            sendToConnection(senderId, new SystemMessage("Game has already started", -1));
        }
        else if(roomService.getRoom().getCurrentSize() < roomService.getRoom().getCapacity()) {
            Connection connection = connections.get(senderId);
            roomService.addConnection(connection);
        }
        else {
            sendToConnection(senderId, new SystemMessage("Room is full", -1));
        }
    }
    public void leaveRoom(int roomId, int senderId) {
        RoomService roomService = rooms.get(roomId);
        roomService.removeConnection(senderId);
        roomService.sendToConnections(new SystemMessage("Player" + " " + senderId + " disconnect", -1));
    }

    public void removeRoom(int roomId) {
        rooms.remove(roomId);
    }
}
