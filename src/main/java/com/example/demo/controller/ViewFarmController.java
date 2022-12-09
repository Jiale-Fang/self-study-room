package com.example.demo.controller;

import com.example.demo.ScoreApplication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ViewFarmController {

    @FXML
    private Button buttonFertilize;

    @FXML
    private Button buttonShop;

    @FXML
    private Button buttonStore;

    @FXML
    private Button buttonWater;

    @FXML
    private AnchorPane paneTree;

    @FXML
    void onButtonFertilize(ActionEvent event) {
        int water = 0;
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT water FROM farm WHERE uid='" + uid + "'");
            rs.next();
            water = rs.getInt("water");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int scoreReputation = ServerProcess.getServer().getScoreReputation(uid);
        if(scoreReputation >= 5) {
            ServerProcess.server.updateScoreReputation(uid, -5);
            water = 3 * (water / 3 + 1);
            try (Statement statement = ServerProcess.getServer().connection.createStatement()) {
                statement.execute("UPDATE farm SET water='" + water + "' WHERE uid='" + uid + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            refresh();
        }

        String alert = scoreReputation >= 5? "Reputation point -5" : "Reputation less than 5";
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 400, 300);
        stage.setScene(scene);
        Label message = new Label();
        message.setAlignment(Pos.CENTER);
        message.setPrefWidth(400);
        message.setText(alert);
        message.setLayoutY(100);
        message.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        pane.getChildren().add(message);
        Button buttonOK = new Button("OK");
        buttonOK.setPrefSize(60, 30);
        buttonOK.setLayoutX(170);
        buttonOK.setLayoutY(180);
        buttonOK.setOnAction(e -> stage.close());
        pane.getChildren().add(buttonOK);
        stage.show();
    }

    @FXML
    void onButtonShop(ActionEvent event) {
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 400, 200);
        stage.setScene(scene);
        stage.setTitle("Shop");
        stage.show();

        File dir = new File("farm/fruit");
        File[] files = dir.listFiles();
        ArrayList<String> listFruit = new ArrayList<>();
        for(File file: files)
            listFruit.add(file.getName());
        double imgScale = 80;
        double imgLayoutX = 0;
        double imgLayoutY = 0;
        for(String imgName: listFruit){
            ImageView iv = new ImageView(new Image("file:farm/fruit/" + imgName, imgScale, imgScale, false, false));
            iv.setLayoutX(imgLayoutX);
            iv.setLayoutY(imgLayoutY);

            iv.setOnMouseClicked(new ImageViewFruitHandler(uid, imgName.substring(0, imgName.length() - 5), this, stage));
            pane.getChildren().add(iv);
            imgLayoutX += imgScale + 15;
        }
    }

    @FXML
    void onButtonStore(ActionEvent event) {
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 600, 400);
        stage.setTitle("Bag");
        stage.setScene(scene);
        stage.show();

        File dir = new File("farm/fruit");
        File[] files = dir.listFiles();
        ArrayList<String> listFruit = new ArrayList<>();
        for(File file: files)
            listFruit.add(file.getName());
        double imgScale = 80;
        double imgLayoutX = 0;
        double imgLayoutY = 0;
        for(String imgName: listFruit){
            ImageView iv = new ImageView(new Image("file:farm/fruit/" + imgName, imgScale, imgScale, false, false));
            iv.setLayoutX(imgLayoutX);
            iv.setLayoutY(imgLayoutY);

            String fruitName = imgName.substring(0, imgName.length() - 5);
            pane.getChildren().add(iv);

            int amount = 0;
            try(Statement statement = ServerProcess.getServer().connection.createStatement()){
                ResultSet rs = statement.executeQuery("SELECT amount FROM fruit WHERE (uid='" + uid + "' and fruit='" + fruitName + "')");
                rs.next();
                amount = rs.getInt("amount");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Label labelAmount = new Label(" * " + amount);
            labelAmount.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
            labelAmount.setLayoutX(imgLayoutX + imgScale + 20);
            labelAmount.setLayoutY(imgLayoutY + imgScale / 2 - 5);
            pane.getChildren().add(labelAmount);

            imgLayoutY += imgScale + 15;
        }
    }

    @FXML
    void onButtonWater(ActionEvent event) {
        int water = 0;
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            ResultSet rs = statement.executeQuery("SELECT water FROM farm WHERE uid='" + uid + "'");
            rs.next();
            water = rs.getInt("water");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int scoreAccumulation = ServerProcess.getServer().getScoreAccumulation(uid);
        if(scoreAccumulation >= 1) {
            ServerProcess.server.updateScoreAccumulation(uid, -1);
            try (Statement statement = ServerProcess.getServer().connection.createStatement()) {
                statement.execute("UPDATE farm SET water='" + (water + 1) + "' WHERE uid='" + uid + "'");
                refresh();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

        String alert = scoreAccumulation >= 1? "Focus point -1" : "Focus point less than 1";
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 400, 300);
        stage.setScene(scene);
        Label message = new Label();
        message.setAlignment(Pos.CENTER);
        message.setPrefWidth(400);
        message.setText(alert);
        message.setLayoutY(100);
        message.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        pane.getChildren().add(message);
        Button buttonOK = new Button("OK");
        buttonOK.setPrefSize(60, 30);
        buttonOK.setLayoutX(170);
        buttonOK.setLayoutY(180);
        buttonOK.setOnAction(e -> stage.close());
        pane.getChildren().add(buttonOK);
        stage.show();
    }

    String uid;

    public ViewFarmController(String id) throws IOException {
        this.uid = id;

        FXMLLoader fxmlLoader = new FXMLLoader(ScoreApplication.class.getResource("view-farm.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Farm");
        stage.setScene(scene);
        stage.show();

        buttonWater.setGraphic(new ImageView(new Image("file:farm/buttonWater.png", 55, 55, false, false)));
        buttonFertilize.setGraphic(new ImageView(new Image("file:farm/buttonFertilize.png", 55, 55, false, false)));
        buttonStore.setGraphic(new ImageView(new Image("file:farm/buttonStore.png", 55, 55, false, false)));
        buttonShop.setGraphic(new ImageView(new Image("file:farm/buttonShop.png", 55, 55, false, false)));
        refresh();
    }

    void refresh(){
        paneTree.getChildren().clear();
        String path = "farm";
        String sql = "SELECT * FROM farm WHERE uid='" + uid + "'";
        String plant = "";
        int water = 0;
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            plant = rs.getString("plant");
            water = rs.getInt("water");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int level = water / 3;
        Image img = new Image("file:" + path + "/" + plant + "/" + level + ".png");
        double imgWidth = img.getWidth();
        double imgHeight = img.getHeight();
        double imgLayoutX = 0;
        double imgLayoutY = 0;
        if(level == 0){
            imgWidth = 200;
            imgHeight = 200;
            imgLayoutX = 100;
            imgLayoutY = 180;
        }
        else{
            imgWidth = 300;
            imgHeight = 300;
            imgLayoutX = 50;
            imgLayoutY = 75;
        }
        ImageView imageViewTree = new ImageView();
        imageViewTree.setImage(new Image(img.getUrl(), imgWidth, imgHeight, false, false));
        imageViewTree.setLayoutX(imgLayoutX);
        imageViewTree.setLayoutY(imgLayoutY);
        paneTree.getChildren().add(imageViewTree);

        if(water / 3 < 2) {
            ProgressBar pb = new ProgressBar();
            pb.setPrefWidth(200);
            pb.setProgress(water % 3 / 3.0);
            pb.setLayoutX(imgLayoutX + imgWidth / 2 - 100);
            pb.setLayoutY(imgLayoutY - 50);
            paneTree.getChildren().add(pb);
        }
        else{
            Button buttonPick = new Button();
            buttonPick.setGraphic(new ImageView(new Image("file:farm/glove.png", 45, 45, false, false)));
            buttonPick.setPrefSize(45,45);
            buttonPick.setLayoutX(imgLayoutX + imgWidth + 20);
            buttonPick.setLayoutY(imgLayoutY + imgHeight / 2);
            buttonPick.setOnAction(new ButtonPickHandler(uid, plant, this));
            paneTree.getChildren().add(buttonPick);
        }
    }

}

class ButtonPickHandler implements EventHandler<ActionEvent>{

    String uid;

    String plant;

    ViewFarmController viewFarmController;

    public ButtonPickHandler(String id, String p, ViewFarmController controller){
        this.uid = id;
        this.plant = p;
        this.viewFarmController = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            statement.execute("UPDATE farm SET water='" + 0 + "' WHERE uid='" + uid + "'");
            ResultSet rs = statement.executeQuery("SELECT amount FROM fruit WHERE (uid='" + uid + "' and fruit='" + plant + "')");
            rs.next();
            int amount = rs.getInt("amount") + 1;
            statement.execute("UPDATE fruit SET amount='" + amount + "' WHERE (uid='" + uid + "' and fruit='" + plant + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        viewFarmController.refresh();

        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 400, 300);
        stage.setScene(scene);
        ImageView fruit = new ImageView(new Image("file:farm/fruit/" + plant + ".jpeg", 80, 80, false, false));
        fruit.setLayoutX(120);
        fruit.setLayoutY(110);
        pane.getChildren().add(fruit);
        ImageView plusOne = new ImageView(new Image("file:farm/plus one.png", 80, 80, false, false));
        plusOne.setLayoutX(210);
        plusOne.setLayoutY(110);
        pane.getChildren().add(plusOne);
        Button buttonOK = new Button("OK");
        buttonOK.setPrefSize(60, 30);
        buttonOK.setLayoutX(170);
        buttonOK.setLayoutY(240);
        buttonOK.setOnAction(e -> stage.close());
        pane.getChildren().add(buttonOK);
        stage.show();
    }
}

class ImageViewFruitHandler implements EventHandler<MouseEvent>{

    String uid;

    String plant;

    ViewFarmController viewFarmController;

    Stage stage;

    public ImageViewFruitHandler(String id, String p, ViewFarmController controller, Stage s){
        this.uid = id;
        this.plant = p;
        this.viewFarmController = controller;
        this.stage = s;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            statement.execute("UPDATE farm SET plant='" + plant + "',water='" + 0 + "' WHERE uid='" + uid + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stage.close();
        viewFarmController.refresh();

        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 400, 300);
        stage.setScene(scene);
        Label message = new Label();
        message.setAlignment(Pos.CENTER);
        message.setPrefWidth(400);
        message.setText("Plant changed!");
        message.setLayoutY(100);
        message.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        pane.getChildren().add(message);
        Button buttonOK = new Button("OK");
        buttonOK.setPrefSize(60, 30);
        buttonOK.setLayoutX(170);
        buttonOK.setLayoutY(180);
        buttonOK.setOnAction(e -> stage.close());
        pane.getChildren().add(buttonOK);
        stage.show();
    }
}
