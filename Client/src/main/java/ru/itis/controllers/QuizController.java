package ru.itis.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import ru.itis.connection.impl.ConnectionHolderImpl;
import ru.itis.models.Question;
import ru.itis.protocol.message.client.RightAnswerMessage;

import java.io.IOException;


public class QuizController {
    @FXML
    private Label labelQuiz;

    @FXML
    private Label labelTimeUp;

    @FXML
    private Button btnAnswer;

    private Question currentQuestion;

    public void initQuestion(Question question) {
        currentQuestion = question;
        labelQuiz.setText(question.getQuestion());
        ToggleGroup group = new ToggleGroup();
        for (int i = 0; i < question.getAnswers().length; i++) {
            RadioButton newAnswer = new RadioButton(question.getAnswers()[i]);
            newAnswer.setId(String.valueOf(i));
            newAnswer.setToggleGroup(group);
            labelQuiz.getScene().getRoot().getChildrenUnmodifiable().add(newAnswer);
        }
        btnAnswer.setDisable(false);
        labelTimeUp.setVisible(false);
    }

    public void answerQuestion(ActionEvent event) {
        ObservableList<Node> nodesOnScreen = btnAnswer.getScene().getRoot().getChildrenUnmodifiable();
        Integer checkedId = Integer.parseInt(nodesOnScreen.stream()
                .filter(it -> it instanceof RadioButton)
                .map(it -> (RadioButton) it)
                .filter(ToggleButton::isSelected)
                .findFirst()
                .get()
                .getId());
        if (checkedId == currentQuestion.getCorrectAnsId()) {
            int playerId = ConnectionHolderImpl.getConnection().getPlayer().getId();
            try {
                ConnectionHolderImpl.getConnection().send(new RightAnswerMessage(playerId, currentQuestion.getPoints()));
            } catch (IOException e) {
                e.printStackTrace(); // todo
            }
        }
    }

    public void timeUp() {
        labelTimeUp.setVisible(true);
        btnAnswer.setDisable(true);
    }
}
