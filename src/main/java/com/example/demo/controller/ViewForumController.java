package com.example.demo.controller;

import com.example.demo.ForumApplication;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ViewForumController {

    @FXML
    public AnchorPane paneTopics;

    @FXML
    private Button buttonSearch;

    @FXML
    private ComboBox<String> comboBoxSelectField;

    @FXML
    private TextField textFieldSearch;

    Topic topicChose;

    String uid; // receive uid from main process

    String getValueOfComboBoxSelectField(){
        return comboBoxSelectField.getValue();
    }

    public ViewForumController(String id) throws IOException {
        this.uid = id;

        FXMLLoader fxmlLoader = new FXMLLoader(ForumApplication.class.getResource("view-forum.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Forum");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(){
        ServerProcess sp = ServerProcess.getServer();
        ArrayList<String> list = new ArrayList<>();
        try(Statement statement = sp.connection.createStatement()){
            String sql = "SELECT DISTINCT field FROM field";
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next())
                list.add(rs.getString("field"));
        }
        catch(SQLException exception){
            exception.printStackTrace();
            System.exit(0);
        }
        ObservableList<String> observableList = FXCollections.observableArrayList(list);
        comboBoxSelectField.setItems(observableList);
    }

    @FXML
    void onButtonSearchAction(ActionEvent event) throws FileNotFoundException {
        paneTopics.getChildren().clear();
        String query = textFieldSearch.getText().toLowerCase();

        String sql = "SELECT DISTINCT field from topic";
        ServerProcess sp = ServerProcess.getServer();
        ArrayList<String> fields = new ArrayList<>();
        try (Statement statement = sp.connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next())
                fields.add(rs.getString("field"));
        } catch (SQLException exception) {
            exception.printStackTrace();
            System.exit(0);
        }

        double layoutX = paneTopics.getLayoutX();
        double layoutY = paneTopics.getLayoutY();
        for(String field : fields) {
            String path = "forum/theme/" + field;
            File dirTheme = new File(path);
            if (!dirTheme.exists()) {
                System.out.println("path doesn't exist.");
                System.exit(0);
            }

            ArrayList<File> fileThemes = new ArrayList<>();
            File[] files = dirTheme.listFiles();
            for (File file : files)
                if (!file.getName().equals(".DS_Store"))
                    fileThemes.add(file);

            ArrayList<String> listThemes = new ArrayList<>();
            ArrayList<String> listTids = new ArrayList<>();
            for (File theme : fileThemes) {
                Scanner scanner = new Scanner(theme);
                StringBuilder buffer = new StringBuilder();
                while (scanner.hasNextLine())
                    buffer.append(scanner.nextLine()).append(" ");
                if (buffer.toString().toLowerCase().contains(query)) {
                    listThemes.add(buffer.toString());
                    listTids.add(theme.getName());
                }
            }

            for (int ind = 0; ind < listThemes.size(); ind++) {
                String theme = listThemes.get(ind);
                String tid = listTids.get(ind);
                tid = tid.substring(0, tid.length() - 4);   // get tid, removing ".txt" from file name
                Topic topic = new Topic(theme, tid, field);
                topic.setOnAction(new TopicHandler(this, topic));
                topic.setLayoutX(layoutX);
                topic.setLayoutY(layoutY);
                topic.setPrefHeight(45);
                layoutY += 46;
                colorTopic(topic);
                paneTopics.getChildren().add(topic);
            }
        }
    }

    public void onButtonPostTopicAction(ActionEvent event) throws IOException {
        if(this.comboBoxSelectField.getValue() != null)
            new ViewPostController(this);
        else{
            Stage stage = new Stage();
            AnchorPane pane = new AnchorPane();
            Scene scene = new Scene(pane, 400, 200);
            stage.setScene(scene);
            Label message = new Label();
            message.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
            message.setText("Please select a field before post!");
            message.setAlignment(Pos.CENTER);
            message.setPrefWidth(400);
            message.setLayoutX(0);
            message.setLayoutY(50);
            pane.getChildren().add(message);
            Button buttonOK = new Button("OK");
            buttonOK.setPrefSize(60, 30);
            buttonOK.setLayoutX(170);
            buttonOK.setLayoutY(120);
            buttonOK.setOnAction(e -> stage.close());
            pane.getChildren().add(buttonOK);
            stage.show();
        }
    }

    public void onComboBoxSelectField(ActionEvent event) throws FileNotFoundException {
        paneTopics.getChildren().clear();
        String field = comboBoxSelectField.getValue();
        String path = "forum/theme/" + field;
        File dirTheme = new File(path);
        if(!dirTheme.exists()){
            System.out.println("path doesn't exist.");
            System.exit(0);
        }
        File[] files = dirTheme.listFiles();
        ArrayList<File> fileThemes = new ArrayList<>();
        for(File file : files)
            if(!file.getName().equals(".DS_Store"))
                fileThemes.add(file);

        ArrayList<String> listThemes = new ArrayList<>();
        for (File theme : fileThemes){
            Scanner scanner = new Scanner(theme);
            StringBuilder buffer = new StringBuilder();
            while(scanner.hasNextLine())
                buffer.append(scanner.nextLine()).append("\n");
            listThemes.add(buffer.toString());
        }

        double layoutX = paneTopics.getLayoutX();
        double layoutY = paneTopics.getLayoutY();
        for(int ind = 0; ind < listThemes.size(); ind++){
            String theme = listThemes.get(ind);
            String tid = fileThemes.get(ind).getName();
            tid = tid.substring(0, tid.length() - 4);   // get tid, removing ".txt" from file name
            Topic topic = new Topic(theme, tid, field);
            topic.setOnAction(new TopicHandler(this, topic));
            topic.setLayoutX(layoutX);
            topic.setLayoutY(layoutY);
            topic.setPrefHeight(45);
            layoutY += 48;
            colorTopic(topic);
            paneTopics.getChildren().add(topic);
        }
    }

    void colorTopic(Topic t){
        t.setStyle("-fx-background-color: linear-gradient(to bottom,#FFFF33,#CCFFFF);");
    }

    void refresh() throws FileNotFoundException {
        paneTopics.getChildren().clear();
        String field = comboBoxSelectField.getValue();
        String path = "forum/theme/" + field;
        File dirTheme = new File(path);
        if(!dirTheme.exists()){
            System.out.println("path doesn't exist.");
            System.exit(0);
        }
        File[] files = dirTheme.listFiles();
        ArrayList<File> fileThemes = new ArrayList<>();
        for(File file : files)
            if(!file.getName().equals(".DS_Store"))
                fileThemes.add(file);

        ArrayList<String> listThemes = new ArrayList<>();
        for (File theme : fileThemes){
            Scanner scanner = new Scanner(theme);
            StringBuilder buffer = new StringBuilder();
            while(scanner.hasNextLine())
                buffer.append(scanner.nextLine()).append("\n");
            listThemes.add(buffer.toString());
        }

        double layoutX = paneTopics.getLayoutX();
        double layoutY = paneTopics.getLayoutY();
        for(int ind = 0; ind < listThemes.size(); ind++){
            String theme = listThemes.get(ind);
            String tid = fileThemes.get(ind).getName();
            tid = tid.substring(0, tid.length() - 4);   // get tid, removing ".txt" from file name
            Topic topic = new Topic(theme, tid, field);
            topic.setOnAction(new TopicHandler(this, topic));
            topic.setLayoutX(layoutX);
            topic.setLayoutY(layoutY);
            topic.setPrefHeight(45);
            layoutY += 48;
            colorTopic(topic);
            paneTopics.getChildren().add(topic);
        }
    }

    void showViewRead() throws IOException {
        ViewReadController viewReadController = new ViewReadController(this);
    }
}

class Topic extends Button{
    private final String tid;

    private final String field;

    Topic(String text, String id, String field){
        super(text);
        this.tid = id;
        this.field = field;
    }

    String getTid(){
        return this.tid;
    }

    String getField(){
        return this.field;
    }
}

class TopicHandler implements EventHandler<ActionEvent>{

    ViewForumController viewForumController;

    Topic topic;

    TopicHandler(ViewForumController controller, Topic t){
        this.viewForumController = controller;
        this.topic = t;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            viewForumController.topicChose = this.topic;
            viewForumController.showViewRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}