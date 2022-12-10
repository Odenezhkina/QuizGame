package ru.itis.utils;

import javafx.scene.control.Alert;
import ru.itis.controllers.QuizController;
import ru.itis.controllers.RoomInfoController;
import ru.itis.models.Player;
import ru.itis.models.Question;
import ru.itis.models.Room;
import ru.itis.utils.exceptions.InvalidStageStateException;

import java.io.IOException;
import java.util.List;

public class UiEventHandler {
    private final UiNavigatorHolder uiNavigatorHolder;

    public UiEventHandler(UiNavigatorHolder uiNavigatorHolder) {
        this.uiNavigatorHolder = uiNavigatorHolder;
    }

    public void startGame() {
    }

    public void endGame() {
    }

    public void showSystemMessage(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null); // default title
        alert.setHeaderText(content);
        alert.showAndWait();
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
//        try {
////            QuizController controller = (QuizController) uiNavigatorHolder.getUiNavigator().navigateFromCurrentStage("quiz-screen.fxml");
////            controller.timeUp();
//        } catch (InvalidStageStateException | IOException e) {
//            showSystemMessage(e.getMessage());
//        }
    }

    private void navigateToRoomInfo(Room room) {
        try {
            RoomInfoController controller = (RoomInfoController) uiNavigatorHolder.getUiNavigator().navigateFromCurrentStage("room-info.fxml");
            controller.initRoomInfo(room);
        } catch (InvalidStageStateException | IOException e) {
            showSystemMessage(e.getMessage());
        }
    }
}
