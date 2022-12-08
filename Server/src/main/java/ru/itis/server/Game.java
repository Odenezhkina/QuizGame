package ru.itis.server;

import ru.itis.models.Player;
import ru.itis.models.Question;

import java.util.HashMap;
import java.util.List;

public class Game {
    private RoomService room;
    private HashMap<Integer, Player> players;
    private List<Question> questions;
    private final int TIME_FOR_ANSWER = 1000;

    public void start(RoomService room, HashMap<Integer,Player> connections) {
        this.room = room;
        this.players = connections;
    }
    public void playerDisconnected(int id){
        if (players.get(id) == null){
            return;
        }
        players.remove(id);
    }
}
