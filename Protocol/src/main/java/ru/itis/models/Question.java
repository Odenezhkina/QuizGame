package ru.itis.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
@Data
public class Question {
    private int points;
    private String question;
    private String[] answers;
    private int correctAnsId;
}

