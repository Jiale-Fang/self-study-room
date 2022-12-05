package com.example.demo;

import com.example.demo.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        HelloController helloController = fxmlLoader.getController();
        Scene scene = new Scene(root, 320, 240);
        stage.setScene(scene);
        helloController.setInitializeData("asdasldk");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}