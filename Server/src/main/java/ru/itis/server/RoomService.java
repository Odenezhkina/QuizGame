package ru.itis.server;

import lombok.Getter;
import ru.itis.connection.Connection;
import ru.itis.models.Player;
import ru.itis.models.Room;
import ru.itis.protocol.message.ContentMessage;
import ru.itis.protocol.message.server.RoomWasUpdatedMessage;
import ru.itis.protocol.message.server.SystemMessage;

import ru.itis.utils.MessageForUser;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@Getter
public class RoomService {
    private final HashMap<Integer, Connection> connections;
    private final Server server;
    private final Room room;
    private Game game;

    public RoomService(int id, String name, int capacity, int currentSize, Server server) {
        room = Room.builder().id(id).name(name).capacity(capacity).currentSize(currentSize).build();
        connections = new HashMap<>();
        this.server = server;
    }

    public void finishGame(){
        game = null;
    }

    public void startGame() {
        game = new Game();
        game.start(this, getPlayers());
    }

    private HashMap<Integer, Player> getPlayers() {
        HashMap<Integer, Player> hashMap = new HashMap<>();
        Collection<Connection> collection = connections.values();
        for (Connection connection : collection) {
            hashMap.put(connection.getId(), Player.builder().id(connection.getId()).username(connection.getPlayer().getUsername()).points(0).build());
        }
        return hashMap;
    }

    public void sendToConnections(ContentMessage message){
        for (Connection con : connections.values()) {
            try {
                con.send(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeConnection(int connectionId) {
        Connection connection = connections.get(connectionId);
        if (connection == null) {
            return;
        }
        if (game != null) {
            game.playerDisconnected(connectionId);
        }
        room.removePlayer(connectionId);
        connections.get(connectionId).getPlayer().setRoomId(-1);
        if (room.getCurrentSize() == 0) {
            server.removeRoom(room.getId());
        }
    }

    public void addConnection(Connection connection) {
        connection.getPlayer().setRoomId(room.getId());
        connections.put(connection.getId(), connection);
        room.addPlayer(connection.getPlayer());
        room.setCurrentSize(room.getCurrentSize() + 1);

        sendToConnections(new RoomWasUpdatedMessage(room, room.getId()));
    }
}
