package ru.itis.utils;

import ru.itis.controllers.FinalScoreController;
import ru.itis.controllers.QuizController;
import ru.itis.controllers.RoomInfoController;
import ru.itis.models.Player;
import ru.itis.models.Question;
import ru.itis.models.Room;
import ru.itis.utils.exceptions.InvalidStageStateException;
import ru.itis.utils.navigation.UiNavigatorHolder;

import java.io.IOException;
import java.util.List;

public class UiEventHandler {
    private final UiNavigatorHolder uiNavigatorHolder;

    public UiEventHandler(UiNavigatorHolder uiNavigatorHolder) {
        this.uiNavigatorHolder = uiNavigatorHolder;
    }

    public void showSystemMessage(String content) {
        new SystemErrorHandler().handleError(content);
    }

    public void joinRoom(Room room) {
        navigateToRoomInfo(room);
    }

    public void roomCreated(Room room) {
        navigateToRoomInfo(room);
    }

    public void showNextQuestion(Question question) {
        try {
            QuizController controller = (QuizController) uiNavigatorHolder.getUiNavigator().navigateFromCurrentStage("quiz-screen.fxml");
            controller.initQuestion(question);
        } catch (InvalidStageStateException | IOException e) {
            showSystemMessage(e.getMessage());
        }
    }

    public void timeUp() {
        try {
            QuizController controller = (QuizController) uiNavigatorHolder.getUiNavigator().navigateFromCurrentStage("quiz-screen.fxml");
            controller.timeUp();
        } catch (InvalidStageStateException | IOException e) {
            showSystemMessage(e.getMessage());
        }
    }

    public void showStats(List<Player> players) {
        try {
            FinalScoreController controller = (FinalScoreController) uiNavigatorHolder.getUiNavigator().navigateFromCurrentStage("final-score.fxml");
            controller.showStats(players);
        } catch (InvalidStageStateException | IOException e) {
            showSystemMessage(e.getMessage());
        }
    }

    private void navigateToRoomInfo(Room room) {
        try {
            RoomInfoController controller = (RoomInfoController) uiNavigatorHolder.getUiNavigator().navigateFromCurrentStage("room-info.fxml");
            controller.initRoomInfo(room);
        } catch (InvalidStageStateException | IOException e) {
            showSystemMessage(e.getMessage());
        }
    }

    public void updateRoom(Room room) {
        try {
            RoomInfoController controller = (RoomInfoController) uiNavigatorHolder.getUiNavigator().navigateFromCurrentStage("room-info.fxml");
            controller.initRoomInfo(room);
        } catch (InvalidStageStateException | IOException e) {
            showSystemMessage(e.getMessage());
        }
    }
}
