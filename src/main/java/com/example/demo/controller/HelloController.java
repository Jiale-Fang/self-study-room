package com.example.demo.controller;

import com.example.demo.util.Time;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private VBox rootLayout;
    @FXML
    private Label timeText;

    Time time = new Time(100);

    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.0),
            e->{
                time.oneSecondPassed();
                timeText.setText(String.valueOf(time.second));
            }));

    public void setInitializeData(String str){
        rootLayout.setUserData(str);
    }

    @FXML
    protected void onHelloButtonClick() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

//        welcomeText.setText("Welcome to JavaFX Application!" + (String) rootLayout.getUserData());
    }

}