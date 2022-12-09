package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import ru.itis.models.Question;
import ru.itis.repository.QuestionRepository;


public class QuizController {
    @FXML
    private Label labelQuiz;

    @FXML
    private ListView<String> listViewAnswers;

    private QuestionRepository questionRepository; // todo how to init?

    public void initQuestion() {
        Question question = questionRepository.getQuestion();
        labelQuiz.setText(question.getQuestion());
        ToggleGroup group = new ToggleGroup();
        for (String answer : question.getAnswers()) {
            RadioButton newAnswer = new RadioButton(answer);
            newAnswer.setToggleGroup(group);
            labelQuiz.getScene().getRoot().getChildrenUnmodifiable().add(newAnswer);
        }
    }

    public void answerQuestion(ActionEvent event) {
        // send message to server (point right answer or not)

        // init new question
        initQuestion();
    }

}
