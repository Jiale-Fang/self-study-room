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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ViewReadController {

    ViewForumController viewForumController;

    String uid;

    double nextReplyLayoutX;
    double nextReplyLayoutY;

    @FXML
    private AnchorPane paneRead;

    @FXML
    private Button buttonReply;

    @FXML
    private TextArea textAreaContent;

    @FXML
    private TextArea textAreaTheme;

    @FXML
    void onButtonReplyAction(ActionEvent event) throws IOException {
        ViewReplyController viewReplyController = new ViewReplyController(this);
    }

    public ViewReadController(ViewForumController controller) throws IOException {
        this.viewForumController = controller;
        this.uid = controller.uid;

        FXMLLoader fxmlLoader = new FXMLLoader(ForumApplication.class.getResource("view-read.fxml"));
        fxmlLoader.setController(this);
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Read");
        stage.setScene(scene);
        stage.show();

        String tid = viewForumController.topicChose.getTid();
        String field = viewForumController.topicChose.getField();

        System.out.println(tid + " " + field);

        // theme
        File themeFile = new File("forum/theme/" + field + "/" + tid + ".txt");
        Scanner scanner = new Scanner(themeFile);
        StringBuilder buffer = new StringBuilder();
        while(scanner.hasNextLine())
            buffer.append(scanner.nextLine()).append("\n");
        String theme = buffer.toString();
        textAreaTheme.setText(theme);
        textAreaTheme.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));

        // content
        File contentFile = new File("forum/content/" + field + "/" + tid + ".txt");
        scanner = new Scanner(contentFile);
        buffer = new StringBuilder();
        while(scanner.hasNextLine())
            buffer.append(scanner.nextLine()).append("\n");
        String content = buffer.toString();
        textAreaContent.setText(content);
        textAreaContent.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        // image
        String imgPath = "forum/img/" + field + "/" + tid;
        double imageWidth = 150;
        double imageHeight = 150;
        double imageLayoutX = 30;
        double imageLayoutY = 430;
        double imageInterval = 15;
        File dirImg = new File(imgPath);
        File[] fileImg = dirImg.listFiles();
        if(fileImg != null) {
            ArrayList<ImageView> listImgView = new ArrayList<>();
            for (File img : fileImg)
                if (!img.getName().equals(".DS_Store"))
                    listImgView.add(new ImageView(new Image("file:" + imgPath + "/" + img.getName(), imageWidth, imageHeight, false, false)));
            for (ImageView imgView : listImgView) {
                imgView.setLayoutX(imageLayoutX);
                imgView.setLayoutY(imageLayoutY);
                imageLayoutX += imageWidth + imageInterval;
                imgView.setOnMouseClicked(new ImageViewHandler(imgView));
                paneRead.getChildren().add(imgView);
            }
        }

        // change buttonReply position
        if(fileImg != null)
            buttonReply.setLayoutY(imageLayoutY + imageHeight + imageInterval * 2);

        // extend paneReply along axis y
        paneRead.setPrefHeight(2000);

        // get rid and user data
        ServerProcess sp = ServerProcess.getServer();
        String sql = "SELECT rid,uid FROM reply WHERE (field='" + field + "' AND tid='" + tid + "')";
        ArrayList<String> listRid = new ArrayList<>();
        ArrayList<String> listUid = new ArrayList<>();
        try(Statement statement = sp.connection.createStatement()){
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                listRid.add(String.valueOf(rs.getInt("rid")));
                listUid.add(rs.getString("uid"));
            }
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }

        // split line
        Label replySign = new Label("Comments");
        replySign.setLayoutX(30);
        replySign.setLayoutY(buttonReply.getLayoutY() + 60);
        replySign.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
        paneRead.getChildren().add(replySign);
        Line splitLine = new Line();
        splitLine.setStartX(30);
        splitLine.setStartY(buttonReply.getLayoutY() + 85);
        splitLine.setEndX(buttonReply.getLayoutX() + buttonReply.getWidth());
        splitLine.setEndY(splitLine.getStartY());
        paneRead.getChildren().add(splitLine);

        // load reply and comments
        double replyLayoutX = 30;
        double replyLayoutY = buttonReply.getLayoutY() + 115;
        for(int ind = 0; ind < listRid.size(); ind++) {
            String rid = listRid.get(ind);
            String uid = listUid.get(ind); // author of this reply

            String replyPath = "forum/reply/" + field + "/" + tid + "/" + rid;

            // ----icon
            double iconWidth = 60;
            double iconHeight = 60;
            String iconPath = "file:images/QQ20221124-3.jpg";
            ImageView icon = new ImageView(new Image(iconPath, iconWidth, iconHeight, false, false));
            icon.setLayoutX(replyLayoutX);
            icon.setLayoutY(replyLayoutY);
            paneRead.getChildren().add(icon);

            // ----name
            Label name = new Label("Uid: " + uid);
            name.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
            name.setLayoutX(replyLayoutX + iconWidth + 20);
            name.setLayoutY(replyLayoutY);
            paneRead.getChildren().add(name);
            Label reputation = new Label("Reputation Lv. " + sp.getLevel(sp.getScoreReputation(uid)));
            reputation.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 13));
            reputation.setLayoutX(name.getLayoutX() + 100);
            reputation.setLayoutY(name.getLayoutY());
            paneRead.getChildren().add(reputation);

            // ----content
            File fileComment = new File(replyPath + "/comment.txt");
            scanner = new Scanner(fileComment);
            buffer = new StringBuilder();
            while (scanner.hasNextLine())
                buffer.append(scanner.nextLine()).append("\n");
            double commentWidth = 600;
            double commentHeight = 150;
            TextArea comment = new TextArea();
            comment.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
            comment.setText(buffer.toString());
            comment.setEditable(false);
            comment.setPrefWidth(commentWidth);
            comment.setPrefHeight(commentHeight);
            comment.setLayoutX(name.getLayoutX());
            comment.setLayoutY(name.getLayoutY() + 30);
            paneRead.getChildren().add(comment);

            // ----likeButton
            double buttonLikeWidth = 30;
            double buttonLikeHeight = 30;
            Button buttonLike = new Button();
            buttonLike.setGraphic(new ImageView(new Image("file:images/buttonLike.jpeg", buttonLikeWidth, buttonLikeHeight, false, false)));
            buttonLike.setLayoutX(comment.getLayoutX() + commentWidth + 20);
            buttonLike.setLayoutY(comment.getLayoutY() + commentHeight - 40);
            buttonLike.setPrefWidth(buttonLikeWidth);
            buttonLike.setPrefHeight(buttonLikeHeight);
            buttonLike.setOnAction(new ButtonLikeHandler(uid));
            paneRead.getChildren().add(buttonLike);

            // ----imageReply
            imgPath = replyPath + "/img";
            imageWidth = 100;
            imageHeight = 100;
            imageLayoutX = comment.getLayoutX();
            imageLayoutY = comment.getLayoutY() + commentHeight + 10;
            imageInterval = 10;
            dirImg = new File(imgPath);
            fileImg = dirImg.listFiles();
            if (fileImg != null) {
                ArrayList<ImageView> listImgView = new ArrayList<>();
                for (File img : fileImg)
                    if (!img.getName().equals(".DS_Store"))
                        listImgView.add(new ImageView(new Image("file:" + imgPath + "/" + img.getName(), imageWidth, imageHeight, false, false)));
                for (ImageView imgView : listImgView) {
                    imgView.setLayoutX(imageLayoutX);
                    imgView.setLayoutY(imageLayoutY);
                    imageLayoutX += imageWidth + imageInterval;
                    imgView.setOnMouseClicked(new ImageViewHandler(imgView));
                    paneRead.getChildren().add(imgView);
                }
            }
            replyLayoutY += fileImg == null? 300 : 375;
        }
        this.nextReplyLayoutX = replyLayoutX;
        this.nextReplyLayoutY = replyLayoutY;
    }

    void refresh(String rid, String tid, String uid, String field) throws FileNotFoundException {
        ServerProcess sp = ServerProcess.getServer();
        String replyPath = "forum/reply/" + field + "/" + tid + "/" + rid;

        // ----icon
        double iconWidth = 60;
        double iconHeight = 60;
        String iconPath = "file:images/QQ20221124-3.jpg";
        ImageView icon = new ImageView(new Image(iconPath, iconWidth, iconHeight, false, false));
        icon.setLayoutX(nextReplyLayoutX);
        icon.setLayoutY(nextReplyLayoutY);
        paneRead.getChildren().add(icon);

        // ----name
        Label name = new Label("Uid: " + uid);
        name.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 15));
        name.setLayoutX(nextReplyLayoutX + iconWidth + 20);
        name.setLayoutY(nextReplyLayoutY);
        paneRead.getChildren().add(name);
        Label reputation = new Label("Reputation Lv. " + sp.getLevel(sp.getScoreReputation(uid)));
        reputation.setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 13));
        reputation.setLayoutX(name.getLayoutX() + 100);
        reputation.setLayoutY(name.getLayoutY());
        paneRead.getChildren().add(reputation);

        // ----content
        File fileComment = new File(replyPath + "/comment.txt");
        Scanner scanner = new Scanner(fileComment);
        StringBuilder buffer = new StringBuilder();
        while (scanner.hasNextLine())
            buffer.append(scanner.nextLine()).append("\n");
        double commentWidth = 600;
        double commentHeight = 150;
        TextArea comment = new TextArea();
        comment.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        comment.setText(buffer.toString());
        comment.setEditable(false);
        comment.setPrefWidth(commentWidth);
        comment.setPrefHeight(commentHeight);
        comment.setLayoutX(name.getLayoutX());
        comment.setLayoutY(name.getLayoutY() + 30);
        paneRead.getChildren().add(comment);

        // ----likeButton
        double buttonLikeWidth = 30;
        double buttonLikeHeight = 30;
        Button buttonLike = new Button();
        buttonLike.setGraphic(new ImageView(new Image("file:images/buttonLike.jpeg", buttonLikeWidth, buttonLikeHeight, false, false)));
        buttonLike.setLayoutX(comment.getLayoutX() + commentWidth + 20);
        buttonLike.setLayoutY(comment.getLayoutY() + commentHeight - 40);
        buttonLike.setPrefWidth(buttonLikeWidth);
        buttonLike.setPrefHeight(buttonLikeHeight);
        buttonLike.setOnAction(new ButtonLikeHandler(uid));
        paneRead.getChildren().add(buttonLike);

        // ----imageReply
        String imgPath = replyPath + "/img";
        double imageWidth = 100;
        double imageHeight = 100;
        double imageLayoutX = comment.getLayoutX();
        double imageLayoutY = comment.getLayoutY() + commentHeight + 10;
        double imageInterval = 10;
        File dirImg = new File(imgPath);
        File[] fileImg = dirImg.listFiles();
        if (fileImg != null) {
            ArrayList<ImageView> listImgView = new ArrayList<>();
            for (File img : fileImg)
                if (!img.getName().equals(".DS_Store"))
                    listImgView.add(new ImageView(new Image("file:" + imgPath + "/" + img.getName(), imageWidth, imageHeight, false, false)));
            for (ImageView imgView : listImgView) {
                imgView.setLayoutX(imageLayoutX);
                imgView.setLayoutY(imageLayoutY);
                imageLayoutX += imageWidth + imageInterval;
                imgView.setOnMouseClicked(new ImageViewHandler(imgView));
                paneRead.getChildren().add(imgView);
            }
        }
        this.nextReplyLayoutY += fileImg == null? 300 : 375;
    }
}

class ButtonLikeHandler implements EventHandler<ActionEvent>{

    String uid;

    ButtonLikeHandler(String id){
        this.uid = id;
    }

    @Override
    public void handle(ActionEvent event) {
        ServerProcess sp = ServerProcess.getServer();
        String sql = "UPDATE score SET reputation='"
                + String.valueOf(sp.getScoreReputation(uid) + 1) + "' WHERE uid='" + uid + "'";
        try(Statement statement = sp.connection.createStatement()){
            statement.execute(sql);
        }
        catch(SQLException exception){
            exception.printStackTrace();
        }
    }
}

class ImageViewHandler implements EventHandler<MouseEvent>{

    ImageView imgView;

    ImageViewHandler(ImageView iv){
        this.imgView = iv;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        Image img = new Image(imgView.getImage().getUrl());
        double imgWidth = img.getWidth() > 900? 900 : img.getWidth();
        double imgHeight = img.getHeight() > 600? 600: img.getHeight();
        img = new Image(imgView.getImage().getUrl(), imgWidth, imgHeight, false, false);
        Stage stage = new Stage();
        Pane pane = new Pane();
        Scene scene = new Scene(pane, imgWidth, imgHeight);
        ImageView iv = new ImageView(img);
        iv.setLayoutX(0);
        iv.setLayoutX(0);
        pane.getChildren().add(iv);
        stage.setScene(scene);
        stage.show();
    }
}
