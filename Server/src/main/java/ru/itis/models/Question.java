package ru.itis.models;

import lombok.Builder;

@Builder
public class Question {
    private int points;
    private String question;
    private String[] answers;
    private int correctAnsId; // индекс списка по сути
}
