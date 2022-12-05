package com.example.demo.controller;

import com.example.demo.ForumApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ViewReplyController {
    ViewReadController viewReadController;

    public Stage primaryStage;

    String tid;

    String field;

    String uid;

    ArrayList<File> listImgUpload;

    double imgLayoutX = 10;
    double imgLayoutY = 0;
    double imageWidth = 100;
    double imageHeight = 100;
    double imageInterval = 15;

    @FXML
    private Button buttonSend;

    @FXML
    private Button buttonUploadImage;

    @FXML
    private TextArea textAreaReply;

    @FXML
    private ImageView imgViewReply;

    @FXML
    private AnchorPane paneImageUpload;

    public ViewReplyController(ViewReadController controller) throws IOException {
        this.viewReadController = controller;
        this.uid = controller.uid;
        this.field = controller.viewForumController.getValueOfComboBoxSelectField();
        this.tid = controller.viewForumController.topicChose.getTid();
        this.listImgUpload = new ArrayList<>();

        FXMLLoader fxmlLoader = new FXMLLoader(ForumApplication.class.getResource("view-reply.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        this.primaryStage = stage;
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Reply");
        stage.setScene(scene);
        stage.show();

        this.textAreaReply.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
    }

    @FXML
    void onButtonSendAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 400, 200);
        Label message = new Label("Send Confirmation!");
        message.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 18));
        message.setLayoutX(110);
        message.setLayoutY(40);
        pane.getChildren().add(message);
        Button buttonYes = new Button("Yes");
        buttonYes.setLayoutX(80);
        buttonYes.setLayoutY(100);
        buttonYes.setPrefSize(80, 30);
        buttonYes.setOnAction(new ButtonYesHandlerReply(this, stage));
        pane.getChildren().add(buttonYes);
        Button buttonNo = new Button("No");
        buttonNo.setLayoutX(250);
        buttonNo.setLayoutY(100);
        buttonNo.setPrefSize(80, 30);
        buttonNo.setOnAction(new ButtonNoHandler(stage));
        pane.getChildren().add(buttonNo);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onButtonUploadImgAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        File imgChose = fileChooser.showOpenDialog(stage);
        listImgUpload.add(imgChose);

        // display the target image
        ImageView imageView = new ImageView(new Image("file:" + imgChose.getAbsolutePath(), imageWidth, imageHeight, false, false));
        imageView.setLayoutX(imgLayoutX);
        imageView.setLayoutY(imgLayoutY);
        imageView.setOnMouseClicked(new ImageViewHandler(imageView));
        paneImageUpload.getChildren().add(imageView);
        imgLayoutX += imageWidth + imageInterval;
    }

    public TextArea getTextAreaContent() {
        return this.textAreaReply;
    }
}

class ButtonYesHandlerReply implements EventHandler<ActionEvent> {
    ViewReplyController viewReplyController;

    Stage stage;

    ButtonYesHandlerReply(ViewReplyController controller, Stage s){
        this.viewReplyController = controller;
        this.stage = s;
    }

    @Override
    public void handle(ActionEvent event) {
        String field = viewReplyController.field;
        if(field == null)   // set default field
            field = "cs";
        String path = "forum/";
        int index = 1;
        String sql = "SELECT rid from reply WHERE (field='" + field + "' AND tid='" + viewReplyController.tid + "')";
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next())
                index += 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // write comment file
            File replyDir = new File(path + "reply/" + field + "/" + viewReplyController.tid);
            if(!replyDir.exists())
                replyDir.mkdir();
            replyDir = new File(replyDir.getPath() + "/" + index);
            if(!replyDir.exists())
                replyDir.mkdir();
            File comment = new File(replyDir.getPath() + "/" + "comment.txt");
            comment.createNewFile();
            Writer writer = new FileWriter(comment);
            writer.write(viewReplyController.getTextAreaContent().getText());
            writer.close();

            // upload image
            for(File image: viewReplyController.listImgUpload){
                File uploadDir = new File(replyDir.getPath() + "/img");
                if(!uploadDir.exists())
                    uploadDir.mkdir();
                File uploadFile = new File(uploadDir.getPath() + "/" + image.getName());
                if(!uploadFile.exists())
                    uploadFile.createNewFile();
                FileInputStream inputStream = new FileInputStream(image);
                FileOutputStream outputStream = new FileOutputStream(uploadFile);
                int length = 0;
                while((length=inputStream.read())!=-1)
                    outputStream.write(length);
                outputStream.close();
                inputStream.close();
            }
        }
        catch (IOException exception){
            exception.printStackTrace();
        }

        // update database
        sql = "INSERT INTO reply VALUES ('" + index + "','" + viewReplyController.tid + "','" + viewReplyController.uid + "','" + field + "')";
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("send a reply");
        stage.close();
        viewReplyController.primaryStage.close();
        try {
            viewReplyController.viewReadController.refresh(String.valueOf(index), viewReplyController.tid, viewReplyController.uid, field);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
