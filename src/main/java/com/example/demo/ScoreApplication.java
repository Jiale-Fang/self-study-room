package com.example.demo;

import com.example.demo.controller.ViewMainController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ScoreApplication extends Application {

    private int userId;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ViewMainController viewMainController = new ViewMainController(String.valueOf(userId));
    }

    public void setUserId(int userId){
        this.userId = userId;
    }
}
