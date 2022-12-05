package ru.itis.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;

@Builder
@Setter
@Getter
public class Room {
    private int id;
    private final String name;
    private final int capacity;
    private int currentSize;
    private final HashMap<Integer, Player> players;

    public Collection<Player> getAllPlayers() {
        return players.values();
    }

    public void addPlayer(Player player) {
        players.put(player.getId(), player);
    }

    public void removePlayer(int id) {
        players.remove(id);
    }
}
