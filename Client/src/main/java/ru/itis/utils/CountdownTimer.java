package ru.itis.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import ru.itis.constants.GameSettings;

public class CountdownTimer extends Label {
    private int counter;
    private boolean started;

    public CountdownTimer() {
        this.counter = GameSettings.TIME_FOR_QUESTION;
        setText(String.valueOf(counter));
    }

    public void start() {
        if (started) {
            return;
        }
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(Duration.seconds(0),
                event -> {
                    setText(String.valueOf(counter--));
                    if (counter <= 0) {
                        timeline.stop();
                    }
                });
        timeline.getKeyFrames().addAll(kf, new KeyFrame(Duration.seconds(1)));
//        timeline.setOnFinished(event -> System.out.println("Done!"));
//        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        started = true;
    }
}
