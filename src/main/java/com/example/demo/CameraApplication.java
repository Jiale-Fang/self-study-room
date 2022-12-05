package com.example.demo;

import com.example.demo.controller.CameraController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class CameraApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("camera-view.fxml"));
        BorderPane root = fxmlLoader.load();
        CameraController cameraController = fxmlLoader.getController();
        Scene scene = new Scene(root, 800, 600);
        scene.getCamera();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}