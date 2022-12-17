package ru.itis.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import ru.itis.connection.impl.ConnectionHolder;
import ru.itis.constants.GameSettings;
import ru.itis.models.Player;
import ru.itis.models.Question;
import ru.itis.protocol.message.client.GetNewQuestionMessage;
import ru.itis.protocol.message.client.RightAnswerMessage;
import ru.itis.utils.SystemErrorHandler;
import ru.itis.utils.exceptions.ConnectionNotInitializedException;

import javax.swing.*;
import java.awt.event.ActionListener;
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
        btnAnswer.setDisable(false);
        labelTimeUp.setVisible(false);

        currentQuestion = question;
        labelQuiz.setText(question.getQuestion());
        ToggleGroup group = new ToggleGroup();
        for (int i = 0; i < question.getAnswers().length; i++) {
            RadioButton newAnswer = new RadioButton(question.getAnswers()[i]);
            newAnswer.setId(String.valueOf(i));
            newAnswer.setToggleGroup(group);
            labelQuiz.getScene().getRoot().getChildrenUnmodifiable().add(newAnswer);
        }

//        startQuizTimer();
        startQuizWarningTimer();
    }

//    private void startQuizTimer() {
//        Timer timer = new Timer(GameSettings.TIME_FOR_QUESTION, new ActionListener() {
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                timeUp();
//            }
//        });
//        timer.start();
//    }

    private void startQuizWarningTimer() {
        Timer timer = new Timer(GameSettings.WARNING_TIME_UP_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                showWarning();
            }
        });
        timer.start();
    }

    private void showWarning() {
        labelTimeUp.setVisible(true);
        labelTimeUp.setText("5 seconds left");
    }

    // if player clicks on button Answer
    public void answerQuestion() {
        ObservableList<Node> nodesOnScreen = btnAnswer.getScene().getRoot().getChildrenUnmodifiable();
        Integer checkedId = Integer.parseInt(nodesOnScreen.stream()
                .filter(it -> it instanceof RadioButton)
                .map(it -> (RadioButton) it)
                .filter(ToggleButton::isSelected)
                .findFirst()
                .get()
                .getId());

        try {
            Player player = ConnectionHolder.getConnection().getPlayer();
            if (checkedId == currentQuestion.getCorrectAnsId()) {
                // update points
                ConnectionHolder.getConnection().getPlayer().setPoints(player.getPoints() + currentQuestion.getPoints());
                // notify if answer is right
                ConnectionHolder.getConnection().send(new RightAnswerMessage(player.getId(), currentQuestion.getPoints()));
            }

        } catch (ConnectionNotInitializedException | IOException e) {
            new SystemErrorHandler().handleError(e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    public void timeUp() {
        labelTimeUp.setText("Time is up");
        labelTimeUp.setVisible(true);
        btnAnswer.setDisable(true);
        try {
            // anyway notify what room need new question
            Player player = ConnectionHolder.getConnection().getPlayer();
            ConnectionHolder.getConnection().send(new GetNewQuestionMessage(player.getRoomId()));
        } catch (IOException | ConnectionNotInitializedException e) {
            new SystemErrorHandler().handleError(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
