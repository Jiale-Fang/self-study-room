package com.example.demo.thread;

import com.example.demo.controller.ChatController;
import com.example.demo.controller.StudyRoomController;
import com.example.demo.constant.MessageTypeConstant;
import com.example.demo.entity.Message;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * This thread always monitor those messages which come from the server
 */
public class ClientThread extends Thread {

    private final ChatController chatController;
    private final StudyRoomController studyRoomController;
    public ObjectInputStream ois;

    public ClientThread(ObjectInputStream ois, StudyRoomController studyRoomController, ChatController chatController) {
        this.ois = ois;
        this.studyRoomController = studyRoomController;
        this.chatController = chatController;
        this.start();
    }

    public void run() {
        while (true) {
            try {
                //Come from server
                Message message = (Message) ois.readObject();
                //JavaFx only allow its own thread to modify the controls' values
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        switch (message.getType()) {
                            case MessageTypeConstant.PRIVATE_CHAT_MESSAGE -> chatController.receiveMessage(message);
                            case MessageTypeConstant.GROUP_CHAT_MESSAGE -> chatController.receiveGroupMessage(message);
                            case MessageTypeConstant.ALL_SEAT_INFO -> studyRoomController.updateAllSeatsInfo(message);
                            case MessageTypeConstant.ONE_SEAT_INFO -> studyRoomController.updateOneSeat(message);
                            case MessageTypeConstant.REMOVE_INFO -> beenRemoved();
                        }
                    }
                });

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void beenRemoved() {
        studyRoomController.beenRemoved();
    }

}