package ru.itis.models;

import lombok.Builder;

import java.util.ArrayList;
@Builder
public class Question {
    private int points;
    private String question;
    private ArrayList<String> answers;
    private int correctAnsId; // индекс списка по сути
}