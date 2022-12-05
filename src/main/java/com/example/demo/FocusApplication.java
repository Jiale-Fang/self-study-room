package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FocusApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(FocusApplication.class.getResource("focus-study-view.fxml"));
        Parent root = fxmlLoader.load();
//        TaskController taskController = fxmlLoader.getController();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Focus Study");
        stage.show();
    }


}
     
