package com.example.demo.controller;

import com.example.demo.ScoreApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewDetailController {

    String uid;

    @FXML
    private Button buttonShowDetail;

    @FXML
    private Pane paneDist;

    @FXML
    private ProgressBar progBarAccu;

    @FXML
    private ProgressBar progBarRepu;

    @FXML
    private Label labelAccu;

    @FXML
    private Label labelRepu;

    public ViewDetailController(String id) throws IOException {
        this.uid = id;

        FXMLLoader fxmlLoader = new FXMLLoader(ScoreApplication.class.getResource("view-detail.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Detail");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onButtonShowDetail(ActionEvent event) {
        ServerProcess sp = ServerProcess.getServer();
        int scoreAccu = sp.getScoreAccumulation(uid);
        int scoreRepu = sp.getScoreReputation(uid);
        labelAccu.setText(scoreAccu + " / " + (scoreAccu + sp.scoreToNext(scoreAccu, sp.getLevel(scoreAccu))));
        labelRepu.setText(scoreRepu + " / " + (scoreRepu + sp.scoreToNext(scoreRepu, sp.getLevel(scoreRepu))));
        new Thread(() -> Platform.runLater(() -> progBarAccu.setProgress(((double)scoreAccu) / (scoreAccu + sp.scoreToNext(scoreAccu, sp.getLevel(scoreAccu)))))).start();
        new Thread(() -> Platform.runLater(() -> progBarRepu.setProgress(((double)scoreRepu) / (scoreRepu + sp.scoreToNext(scoreRepu, sp.getLevel(scoreRepu)))))).start();
        viewDistribution(paneDist, uid);
    }

    ProgressBar componentProgressBar(int current, int max){
        return new ProgressBar(((double)current) / max);
    }

    void viewDistribution(Pane pane, String uid){
        ServerProcess server = ServerProcess.getServer();

        ArrayList<Integer> listScore = new ArrayList<>();
        ArrayList<String> listField = new ArrayList<>();
        HashMap<String, Integer> dist = server.getDistribution(uid);
        int totalScore = 0;
        for(String field : dist.keySet()) {
            listField.add(field);
            listScore.add(dist.get(field));
            totalScore += dist.get(field);
        }
        double interval = 0.04 * pane.getWidth();
        double height = pane.getHeight() * 0.9;
        double width = (pane.getWidth() - 5.0 * interval) / 6.0;

        Color[] colors = {Color.BLUE, Color.RED, Color.ORANGE, Color.GREEN};

        for(int ind = 0; ind < listField.size(); ind++){
            double relativeHeight = height * listScore.get(ind) / totalScore;
            Text text = new Text();
            text.setFont(Font.font ("Verdana", 20));
            text.setText(listField.get(ind) + " " + listScore.get(ind));
            text.setX(interval * (ind + 1) + width * ind + 10);
            text.yProperty().bind(pane.heightProperty().subtract(relativeHeight + 10));
            pane.getChildren().add(text);

            Rectangle rectangle = new Rectangle();
            rectangle.setX(interval * (ind + 1) + width * ind);
            rectangle.yProperty().bind(pane.heightProperty().subtract(relativeHeight));
            rectangle.setWidth(width);
            rectangle.setHeight(relativeHeight);
            rectangle.setStroke(Color.BLACK);
            ind = ind % colors.length;
            rectangle.setFill(colors[ind]);
            pane.getChildren().add(rectangle);
        }
    }
}
