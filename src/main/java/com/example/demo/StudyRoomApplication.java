package com.example.demo;

import com.example.demo.chat.Client;
import com.example.demo.controller.ChatController;
import com.example.demo.controller.StudyRoomController;
import com.example.demo.service.StudyRoomService;
import com.example.demo.service.impl.StudyRoomServiceImpl;
import com.example.demo.thread.ClientThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class StudyRoomApplication extends Application{

    private final static StudyRoomService studyRoomService = new StudyRoomServiceImpl();
    private int userId;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudyRoomApplication.class.getResource("study-room-view.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
            StudyRoomController studyRoomController = fxmlLoader.getController();
            Scene scene = new Scene(root, 850, 600);
            Stage stage = new Stage();
            stage.setTitle("Study Room");
            stage.setScene(scene);
            stage.show();
            Client client = new Client(userId);
            studyRoomController.setOos(client.oos);

            //Initialize chat
            FXMLLoader chatFxmlLoader = new FXMLLoader(ChatApplication.class.getResource("chatroom-view.fxml"));
            Parent chatRoot = chatFxmlLoader.load();
            ChatController chatController = chatFxmlLoader.getController();
            chatController.setOos(client.oos);
            chatController.initializeData(userId);
            new ClientThread(client.ois, studyRoomController, chatController);
            //Set root
            studyRoomController.initializeChat(chatRoot);
            studyRoomController.setChatController(chatController);
            studyRoomController.initializeData(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        launch();
//        openStudyRoom();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


//    public static void openStudyRoom() {
//        FXMLLoader fxmlLoader = new FXMLLoader(StudyRoomApplication.class.getResource("study-room-view.fxml"));
//        Parent root = null;
//        try {
//            root = fxmlLoader.load();
//            StudyRoomController studyRoomController = fxmlLoader.getController();
//            Scene scene = new Scene(root, 850, 600);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//            Client client = new Client();
//            studyRoomController.setOos(client.oos);
//
//            //Initialize chat
//            FXMLLoader chatFxmlLoader = new FXMLLoader(GroupChatApplication.class.getResource("chatroom-view.fxml"));
//            Parent chatRoot = chatFxmlLoader.load();
//            GroupChatController groupChatController = chatFxmlLoader.getController();
//            groupChatController.setOos(client.oos);
//            new GroupClientThread(client.ois, studyRoomController, groupChatController);
//            //Set root
//            studyRoomController.setChatRoot(chatRoot);
//            studyRoomController.setChatController(groupChatController);
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}