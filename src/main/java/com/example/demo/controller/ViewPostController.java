package com.example.demo.controller;

import com.example.demo.ForumApplication;
import javafx.application.Application;
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

public class ViewPostController {

    ViewForumController viewForumController;

    String uid;

    String field;

    Stage primaryStage;

    ArrayList<File> listImgUpload;

    double imgLayoutX = 0;
    double imgLayoutY = 0;
    double imageWidth = 100;
    double imageHeight = 100;
    double imageInterval = 15;

    @FXML
    private ImageView imgViewPost;

    @FXML
    private Button buttonPost;

    @FXML
    private Button buttonUploadImg;

    @FXML
    private TextArea textAreaContent;

    @FXML
    private AnchorPane paneImageUpload;

    TextArea getTextAreaContent(){
        return this.textAreaContent;
    }

    @FXML
    private TextArea textAreaTheme;

    TextArea getTextAreaTheme(){
        return this.textAreaTheme;
    }

    public ViewPostController(ViewForumController controller) throws IOException {
        this.viewForumController = controller;
        this.uid = controller.uid;
        this.field = controller.getValueOfComboBoxSelectField();
        this.listImgUpload = new ArrayList<>();

        FXMLLoader fxmlLoader = new FXMLLoader(ForumApplication.class.getResource("view-post.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        this.primaryStage = stage;
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Post");
        stage.setScene(scene);
        stage.show();

        this.textAreaTheme.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
        this.textAreaContent.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 14));
    }

    @FXML
    void onButtonPostAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, 400, 200);
        Label message = new Label("Post Confirmation!");
        message.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 18));
        message.setLayoutX(110);
        message.setLayoutY(40);
        pane.getChildren().add(message);
        Button buttonYes = new Button("Yes");
        buttonYes.setLayoutX(80);
        buttonYes.setLayoutY(100);
        buttonYes.setPrefSize(80, 30);
        buttonYes.setOnAction(new ButtonYesHandler(this, stage));
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
        if(listImgUpload == null)
            listImgUpload = new ArrayList<>();
        if(imgChose != null) {
            listImgUpload.add(imgChose);

            // display the target image
            ImageView imageView = new ImageView(new Image("file:" + imgChose.getAbsolutePath(), imageWidth, imageHeight, false, false));
            imageView.setLayoutX(imgLayoutX);
            imageView.setLayoutY(imgLayoutY);
            imageView.setOnMouseClicked(new ImageViewHandler(imageView));
            paneImageUpload.getChildren().add(imageView);
            imgLayoutX += imageWidth + imageInterval;
        }
    }
}

class ButtonNoHandler implements EventHandler<ActionEvent>{
    Stage stage;

    ButtonNoHandler(Stage s){
        this.stage = s;
    }

    @Override
    public void handle(ActionEvent event) {
        stage.close();
    }
}

class ButtonYesHandler implements EventHandler<ActionEvent>{
    ViewPostController viewPostController;

    Stage stage;

    ButtonYesHandler(ViewPostController controller, Stage s){
        this.viewPostController = controller;
        this.stage = s;
    }

    @Override
    public void handle(ActionEvent event) {
        String field = viewPostController.field;
        if(field == null)   // set default field
            field = "cs";
        String path = "forum/";
        int index = 1;
        String sql = "SELECT tid from topic WHERE field='" + field + "'";
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next())
                index += 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            // write theme file
            File theme = new File(path + "theme/" + field + "/" + index + ".txt");
            theme.createNewFile();
            Writer writer = new FileWriter(theme);
            writer.write(viewPostController.getTextAreaTheme().getText());
            writer.close();

            // write content file
            File content = new File(path + "content/" + field + "/" + index + ".txt");
            content.createNewFile();
            writer = new FileWriter(content);
            writer.write(viewPostController.getTextAreaContent().getText());
            writer.close();

            // upload image
            for(File image: viewPostController.listImgUpload){
                File uploadDir = new File(path + "img/" + field + "/" + index);
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
        sql = "INSERT INTO topic VALUES ('" + index + "','" + field + "','" + viewPostController.uid + "')";
        try(Statement statement = ServerProcess.getServer().connection.createStatement()){
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("post a topic");
        stage.close();
        viewPostController.primaryStage.close();
        try {
            viewPostController.viewForumController.refresh();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
