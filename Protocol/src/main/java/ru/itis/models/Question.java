package ru.itis.models;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Builder
@Getter
public class Question {
    private int points;
    private int timeLimit;
    private String question;
    private ArrayList<String> answers;
    private boolean[] correctAnswers;
}

