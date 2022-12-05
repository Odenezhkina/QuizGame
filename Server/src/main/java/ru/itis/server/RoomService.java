package ru.itis.server;

import ru.itis.messages.Message;
import ru.itis.messages.ServerMessage;
import ru.itis.models.Player;
import ru.itis.models.Room;
import ru.itis.utils.MessageForUser;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class RoomService {
    private final HashMap<Integer, Connection> connections;
    private final Server server;
    private final Room room;
    private Game game;

    public RoomService(int id, String name, int capacity, int currentSize, Server server) {
        room = Room.builder()
                .id(id)
                .name(name)
                .capacity(capacity)
                .currentSize(currentSize)
                .build();
        connections = new HashMap<>();
        this.server = server;
    }

    public void handMessage(Message message) throws IOException {
        switch (message.getType()) {
            case PLAYER_DISCONNECT -> removeConnection(message.getSenderId());
            case GAME_START -> {
                MessageForUser answer = startGame();
                if (answer != null && !answer.isSuccessful()) {
                    sendToConnection(message.getSenderId(), new ServerMessage(answer.getDescription(), message.getSenderId()));
                }
            }
        }
    }

    public MessageForUser startGame(){
        if (game != null) {
            return null;
        }
        if(connections.size()<2) {
            return new MessageForUser(false, "Для старта игры нужно как минимум 2 игрока!");
        }
        if(connections.size() > room.getCapacity()) {
            return new MessageForUser(false, "Слишком много человек для комнаты!");
        }
        game = new Game();
        game.start(this,getPlayers());
        return new MessageForUser(true);
    }
    private HashMap<Integer,Player> getPlayers(){
        HashMap<Integer,Player> hashMap = new HashMap<>();
        Collection<Connection> collection = connections.values();
        for(Connection connection :collection){
            hashMap.put(connection.getId(), Player.builder()
                    .id(connection.getId())
                    .username(connection.getUser().getUsername())
                    .points(0)
                    .build());
        }
        return hashMap;
    }


    public void sendToConnection(int connectionId, Message message){
        try {
            Connection con = connections.get(connectionId);
            if (con.isConnected()) con.send(message);
        }
        catch (IOException e) {
            removeConnection(connectionId);
        }
    }

    public void removeConnection(int connectionId)  {
        Connection connection = connections.get(connectionId);
    }
}

