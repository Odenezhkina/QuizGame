package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.itis.models.Question;


public class QuizController {
    @FXML
    private Label labelQuiz;

    @FXML
    private Label labelTimeUp;

    @FXML
    private ListView<String> listViewAnswers;

    @FXML
    private Button btnAnswer;

    public void initQuestion(Question question) {
        labelQuiz.setText(question.getQuestion());
        ToggleGroup group = new ToggleGroup();
        for (String answer : question.getAnswers()) {
            RadioButton newAnswer = new RadioButton(answer);
            newAnswer.setToggleGroup(group);
            labelQuiz.getScene().getRoot().getChildrenUnmodifiable().add(newAnswer);
        }
        btnAnswer.setDisable(false);
        labelTimeUp.setVisible(false);
    }

    public void answerQuestion(ActionEvent event) {
        // todo send message to server (point right answer or not)
    }

    public void timeUp() {
        labelTimeUp.setVisible(true);
        btnAnswer.setDisable(true);
    }
}
