package ru.itis.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ru.itis.models.Question;


public class QuizController {
    @FXML
    private Label labelQuiz;

    public void initQuestion(){
        Question.builder().question("ll");

    }

}
