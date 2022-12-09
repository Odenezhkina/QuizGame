package ru.itis.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;

@Builder
@Getter
@Data
public class Game {
    private int currentQuestion = 0;
    private ArrayList<Question> questionList;
}
