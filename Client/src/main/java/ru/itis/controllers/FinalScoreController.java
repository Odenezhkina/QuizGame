package ru.itis.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import ru.itis.models.Player;
import ru.itis.utils.UiNavigatorHolder;

import java.util.Comparator;
import java.util.List;

public class FinalScoreController {
    @FXML
    private GridPane gridPane;

    public void showStats(List<Player> playerList) {
        // 1  | score | Username
        gridPane = new GridPane();
        // определения столбцов
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(10);
        gridPane.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(20);
        gridPane.getColumnConstraints().add(column2);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(70);
        gridPane.getColumnConstraints().add(column3);

        // определения строк
        double minH = 20.0;
        double maxH = 30.0;
        double prefH = 25.0;
        // for each row??
        RowConstraints row1 = new RowConstraints(minH, prefH, maxH);
        gridPane.getRowConstraints().add(row1);


        playerList.sort(Comparator.comparingInt(Player::getPoints));

        gridPane.setGridLinesVisible(true);
        gridPane.add(new Label("Place"), 0, 0);
        gridPane.add(new Label("Score"), 1, 0);
        gridPane.add(new Label("Username"), 2, 0);
        for (int i = 1; i <= playerList.size(); i++) {
            Player currPlayer = playerList.get(i - 1);
            gridPane.add(new Label(Integer.toString(i)), 0, i);
            gridPane.add(new Label(Integer.toString(currPlayer.getPoints())), 1, i);
            gridPane.add(new Label(currPlayer.getUsername()), 2, i);
        }
    }

//    public void backToRoomInfo(ActionEvent event) {
//        new UiNavigatorHolder().getUiNavigator().na
//    }
}
