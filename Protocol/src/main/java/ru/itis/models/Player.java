package ru.itis.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Player {
    private String username;
    private int points;
}
