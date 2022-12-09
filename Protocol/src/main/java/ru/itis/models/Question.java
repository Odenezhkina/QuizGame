package ru.itis.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;

@Builder
@Getter
@Data
public class Question {
    private int points;
    private int timeLimit;
    private String question;
    private ArrayList<String> answers;
    private boolean[] correctAnswers;
}

