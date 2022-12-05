package ru.itis.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Player {
    private int id;
    private String username;
    private int points;
    private int roomId;
}
