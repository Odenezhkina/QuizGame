package ru.itis.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
public class Player {
    private int id;
    private String username;
    private int points;
    private int roomId;
}
