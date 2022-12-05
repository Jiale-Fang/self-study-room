package com.example.demo.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.ResourceBundle;

import com.example.demo.FocusApplication;
import com.example.demo.PunishApplication;
import com.example.demo.entity.TaskData;
import com.example.demo.pojo.Task;
import com.example.demo.service.StudyRoomService;
import com.example.demo.service.impl.StudyRoomServiceImpl;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TaskController implements Initializable{

    @FXML
    private AnchorPane rootLayout;

//    private FXMLLoader focusStudyViewFxml;
//
//    private Scene focusStudyViewScene = null;

    private TaskData taskData;

    private static final StudyRoomService studyRoomService = new StudyRoomServiceImpl();

    @FXML
    Label myMessage;

    public void clickMe() {
        Random rand = new Random();
        int myRand = rand.nextInt(100);
        myMessage.setText("Random number is" + Integer.toString(myRand));
    }

    @FXML
    TextField Message;

    public void clickStudy() {
        Message.setText("study");
    }

    public void clickRead() {
        Message.setText("read");
    }

    public void clickWork() {
        Message.setText("work");
    }

    public void clickReview() {
        Message.setText("review");
    }

    @FXML
    Slider focus;
    @FXML
    Slider rest;
    @FXML
    CheckBox discussion;
    @FXML
    CheckBox light;
    @FXML
    CheckBox immersion;
    @FXML
    Label dis;
    @FXML
    Label lig;
    @FXML
    Label imm;
    @FXML
    Label time;

    private int userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskData = new TaskData();
    }

    public void setInitializeData(Integer userId) {
        this.userId = userId;
    }

    public void clickPrint() {
        System.out.println("test:" + userId);
        LocalDateTime localDateTime = LocalDateTime.now();
        Task task = new Task();
        task.setUid(userId);
        task.setRoomId(1);
        task.setCreateTime(localDateTime);
        task.setExpireTime(localDateTime.plusMinutes((long) focus.getValue()));
        task.setFinished(false);
        task.setGoal(Message.getText());

        int taskId = studyRoomService.startTask(task);
        taskData.setTaskId(taskId);
        taskData.setGoal(Message.getText());
        taskData.setFocusTime((int) focus.getValue());
        taskData.setRestTime((int) rest.getValue());
        taskData.setDiscussion(dis.getText() + " " + discussion.isSelected());
        taskData.setLight(lig.getText() + " " + light.isSelected());
        taskData.setImmersion(imm.getText() + " " + immersion.isSelected());
        openFocusView();
    }

    private void openFocusView(){
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(FocusApplication.class.getResource("focus-study-view.fxml"));
            Parent root = null;
            root = fxmlLoader.load();
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.setTitle("Focus Study");
            stage.show();
            TaskController taskController = fxmlLoader.getController();
            taskController.setTaskData(taskData);
            Stage taskStage = (Stage) rootLayout.getScene().getWindow();
            taskStage.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setTaskData(TaskData taskData) {
        this.taskData = taskData;
    }

    @FXML
    Label goal;
    @FXML
    Label type;

    public void clickBegin() {
        int t1 = taskData.getFocusTime() - 1;
        time.setText("Remain Focus Time ：" + Integer.toString(t1) + " min " + Integer.toString(TaskData.second) + " sec");
        goal.setText("Today Focus Goal : " + taskData.getGoal());
        if (taskData.getDiscussion().equals("Discussion : true")) {
            type.setText("Focus Type : Discussion");
        }
        if (taskData.getLight().equals("Light study : true")) {
            type.setText("Focus Type : Light study");
        }
        if (taskData.getImmersion().equals("immersion study : true")) {
            type.setText("Focus Type : immersion Study");
        }

        EventHandler<ActionEvent> ee = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                TaskData.second--;
                time.setText("Remain Focus Time ：" + Integer.toString(taskData.getFocusTime() - 1) + " min " + Integer.toString(TaskData.second) + " sec");
                if (TaskData.second == 0) {
                    TaskData.second = 60;
                    taskData.setFocusTime(taskData.getFocusTime() - 1);
                    if (taskData.getFocusTime() == 0) {
                        studyRoomService.finishTask(taskData.getTaskId());
                        System.out.println("Finish!!!!!");
                    }
                }
            }
        };
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        ee
                ));
        timeline.setCycleCount(60 * (t1 + 1));
        timeline.play();
    }

    public void clickOver() {
        Stage stage = new Stage();
        PunishApplication punishApplication = new PunishApplication();
        try {
            if (taskData.getFocusTime() > 0) {
                punishApplication.start(stage);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    @FXML
    public void clickEnd()
    {
        System.out.println(111);
    }

}


