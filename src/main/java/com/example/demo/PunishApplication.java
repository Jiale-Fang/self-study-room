package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PunishApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("punish.fxml"));
	     Scene scene=new Scene(root,254,381);
	     
	     stage.setScene(scene);
	     stage.setTitle("Punishment");
	     stage.show();
		
	}

}
