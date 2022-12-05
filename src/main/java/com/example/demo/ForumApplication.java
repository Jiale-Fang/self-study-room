package com.example.demo;

import com.example.demo.controller.ViewForumController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ForumApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private int userId;

    @Override
    public void start(Stage primaryStage) throws IOException {
        ViewForumController viewForumController = new ViewForumController(String.valueOf(userId));
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
