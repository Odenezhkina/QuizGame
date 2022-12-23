package ru.itis.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ru.itis.connection.impl.ConnectionHolder;
import ru.itis.constants.GameSettings;
import ru.itis.models.Player;
import ru.itis.models.Question;
import ru.itis.protocol.message.client.RightAnswerMessage;
import ru.itis.utils.CountdownTimer;
import ru.itis.utils.SystemErrorHandler;
import ru.itis.utils.exceptions.ConnectionNotInitializedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class QuizController {
    @FXML
    private Label labelTimer;

    @FXML
    private VBox vboxAnswerVariants;
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
            RadioButton variant = new RadioButton(question.getAnswers()[i]);
            variant.setId(String.valueOf(i));
            variant.setToggleGroup(group);
            vboxAnswerVariants.getChildren().add(variant);
        }

        startQuizTimer();
        startQuizWarningTimer();
    }

    private void startQuizTimer() {
        CountdownTimer timer = new CountdownTimer(labelTimer);
        timer.start();
    }

    private void startQuizWarningTimer() {
        Timer timer = new Timer(GameSettings.WARNING_TIME_UP_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Platform.runLater(() -> {
                    showWarning();
                });
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
        ObservableList<Node> nodesOnScreen = vboxAnswerVariants.getChildren();
        btnAnswer.getStyleClass().add("btn-primary-answered");
        btnAnswer.setText("Answer saved");

        Optional<RadioButton> selectedRb = nodesOnScreen.stream()
                .filter(it -> it instanceof RadioButton)
                .map(it -> (RadioButton) it)
                .filter(ToggleButton::isSelected)
                .findAny();

        if (selectedRb.isPresent()) {
            try {
                Player player = ConnectionHolder.getConnection().getPlayer();
                if (Objects.equals(selectedRb.get().getId(), String.valueOf(currentQuestion.getCorrectAnsId()))) {
                    // update points
                    ConnectionHolder.getConnection().getPlayer().setPoints(player.getPoints() + currentQuestion.getPoints());
                    // notify if answer is right
                    ConnectionHolder.getConnection().send(new RightAnswerMessage(player.getId(), currentQuestion.getPoints()));
                }

            } catch (ConnectionNotInitializedException | IOException e) {
                new SystemErrorHandler().handleError(e.getMessage(), Alert.AlertType.ERROR);
            }
        }

    }

    public void timeUp() {
        labelTimeUp.setText("Time is up");
        labelTimeUp.setVisible(true);
        btnAnswer.setDisable(true);
//        try {
//            // anyway notify what room need new question
//            Player player = ConnectionHolder.getConnection().getPlayer();
//            ConnectionHolder.getConnection().send(new GetNewQuestionMessage(player.getRoomId()));
//        } catch (IOException | ConnectionNotInitializedException e) {
//            new SystemErrorHandler().handleError(e.getMessage(), Alert.AlertType.ERROR);
//        }
    }
}
