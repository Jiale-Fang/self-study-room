package com.example.demo.controller;

import com.example.demo.ForumApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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

    String uid = "123"; // receive uid from main process

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
            String sql = "SELECT DISTINCT field from topic";
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
        ViewPostController viewPostController = new ViewPostController(this);
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