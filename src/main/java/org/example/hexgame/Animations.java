package org.example.hexgame;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import java.util.Map;

public class Animations {
    //This method adds a scaling animation to a button when the mouse cursor hovers over it.
    // When the cursor is over the button, the button's size increases (scaled to 1.5 times its original size), and when the cursor leaves the button, it returns to its original size (scaled to 1.0).
    protected static void addScaleAnimation(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
            st.setToX(1.5);
            st.setToY(1.5);
            st.play();
        });

        button.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
    //This method adds an animation to the given polygons (Polyline).
    // The polygons transition to a specific opacity value (0.3) over a certain duration and then return to their original opacity.
    // This animation is applied separately to each polygon.
    protected static void animationPolylines(Map<Polyline, Integer[]> cellCoordinates){
        Timeline timeline = new Timeline();
        Duration duration = Duration.millis(800);

        for (Polyline polyline : cellCoordinates.keySet()) {
            FadeTransition fadeTransition = new FadeTransition(duration, polyline);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.3);
            fadeTransition.setAutoReverse(true);
            fadeTransition.setCycleCount(2);

            KeyValue keyValue = new KeyValue(polyline.opacityProperty(), 0.3);
            KeyFrame keyFrame = new KeyFrame(duration, keyValue);
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);
        timeline.play();
    }
    //This method rotates the given polygons (Polyline) at a specified angle.
    //Each polygon rotates at a specific angle (180 degrees) over a certain duration. This animation is applied separately to each polygon.
    protected static void rotatePolyline(Map<Polyline, Integer[]> cellCoordinates) {
        Duration duration = Duration.millis(800);
        for (Polyline polyline : cellCoordinates.keySet()) {
            int angle = 180;
            RotateTransition rotateTransition = new RotateTransition(duration,polyline);
            rotateTransition.setByAngle(angle);
            rotateTransition.setCycleCount(2);
            rotateTransition.play();
        }
    }
    //This method adds an animation of fireworks of a specific color to the game panel.
    // Each firework moves in a random direction for a certain distance while simultaneously having a loop of growing and fading.
    // This animation repeats 8 times.
    protected static void playFireworksAnimation(Pane gamePane, Color color) {
        for (int i = 0; i < 8; i++) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(i * 300), event -> {
                for (int j = 0; j < 30; j++) {
                    Circle circle = new Circle(3, color);
                    circle.setCenterX(gamePane.getWidth() / 2);
                    circle.setCenterY(gamePane.getHeight() / 2);

                    gamePane.getChildren().add(circle);

                    double angle = Math.random() * 2 * Math.PI;
                    double distance = 1000 + Math.random() * 400;

                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2.5), circle);
                    translateTransition.setByX(Math.cos(angle) * distance);
                    translateTransition.setByY(Math.sin(angle) * distance);

                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2.5), circle);
                    fadeTransition.setFromValue(1.0);
                    fadeTransition.setToValue(0.0);

                    ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2.5), circle);
                    scaleTransition.setToX(20);
                    scaleTransition.setToY(20);

                    ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition, scaleTransition);
                    parallelTransition.setOnFinished(e -> gamePane.getChildren().remove(circle));
                    parallelTransition.play();

                }


            }));
            timeline.play();
        }
    }

}