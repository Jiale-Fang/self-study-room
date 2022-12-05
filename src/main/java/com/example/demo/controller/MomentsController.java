package com.example.demo.controller;

import com.example.demo.MomentsApplication;
import com.example.demo.service.MomentsService;
import com.example.demo.service.impl.MomentsServiceImpl;
import com.example.demo.vo.MomentsVO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MomentsController implements Initializable {

    private int userId = 7;
    @FXML
    private HBox firstHBox;
    @FXML
    private HBox secondHBox;
    @FXML
    private HBox thirdHBox;

    private static final MomentsService momentsService = new MomentsServiceImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<MomentsVO> momentsVOList = momentsService.getAllMoments();
        System.out.println(momentsVOList);
        for (int i = 0; i < momentsVOList.size(); i++) {
            selectHBox(momentsVOList, i);
        }
    }

    public void selectHBox(List<MomentsVO> momentsVOList, int num) {
        //Make sure every line has a similar number of moments (3 lines)
        switch (num % 3) {
            case 0 -> initializeMoments(secondHBox, momentsVOList.get(num));
            case 1 -> initializeMoments(firstHBox, momentsVOList.get(num));
            case 2 -> initializeMoments(thirdHBox, momentsVOList.get(num));
        }
    }

    public void initializeMoments(HBox rootHBox, MomentsVO momentsVO) {
        VBox vBox = new VBox();
        vBox.setMinSize(350, 190);
        vBox.setStyle("-fx-background-color: #FFF8DC;");
        vBox.setAlignment(Pos.CENTER);
        HBox titleBox = new HBox();
        HBox mainBox = new HBox();
        HBox nameBox = new HBox();
        titleBox.setMaxSize(330, 55);
//        titleBox.setMaxWidth(280);
        mainBox.setMaxSize(330, 160);
//        mainBox.setMaxWidth(280);
        nameBox.setMaxSize(330, 55);
//        nameBox.setMaxWidth(280);
        titleBox.setStyle("-fx-background-color: #FFFFF0;");
        mainBox.setStyle("-fx-background-color: #FFFFF0;");
        nameBox.setStyle("-fx-background-color: #FFFFF0;");

        titleBox.setAlignment(Pos.CENTER);
        mainBox.setAlignment(Pos.CENTER);
        nameBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label();
        Label nameLabel = new Label();
        titleLabel.setText(momentsVO.getTitle());
        nameLabel.setText(momentsVO.getUsername());
        titleLabel.setStyle("-fx-text-fill: #CD853F; -fx-font-size: 18px; -fx-font-weight: BOLD");
        titleLabel.setWrapText(true);
        nameLabel.setStyle("-fx-text-fill: #CD853F; -fx-font-size: 16px");

        Image coverImg = new Image(MomentsApplication.class.getResource(momentsVO.getCover()).toString(), 330, 150, true, true);
        ImageView coverImageView = new ImageView(coverImg);
        coverImageView.setStyle("-fx-cursor: HAND");
        Image cloverImg = new Image(MomentsApplication.class.getResource("images/moments/clover.png").toString(), 20, 20, true, true);
        ImageView cloverImageView = new ImageView(cloverImg);
        cloverImageView.setStyle("-fx-cursor: HAND");

        Image commentImg = new Image(MomentsApplication.class.getResource("images/moments/comment.png").toString(), 20, 20, true, true);
        ImageView commentImageView = new ImageView(commentImg);
        commentImageView.setStyle("-fx-cursor: HAND");

        VBox cloverVBox = new VBox();
        cloverVBox.setAlignment(Pos.CENTER);
        Label cloverLabel = new Label();
        cloverLabel.setId(String.valueOf(momentsVO.getId()));
        cloverLabel.setText(String.valueOf(momentsVO.getLikes()));
        cloverLabel.setStyle("-fx-font-weight: BOLD; -fx-text-fill: red");
        cloverImageView.setOnMouseClicked((e) -> {
            thumbUpOrDown(cloverLabel);
        });
        cloverVBox.getChildren().addAll(cloverLabel, cloverImageView);


        VBox commentVBox = new VBox();
        commentVBox.setAlignment(Pos.CENTER);
        Label commentLabel = new Label();
        commentLabel.setText(String.valueOf(momentsVO.getCommentCnt()));
        commentLabel.setStyle("-fx-font-weight: BOLD; -fx-text-fill: red");
        commentVBox.getChildren().addAll(commentLabel, commentImageView);

        titleBox.getChildren().addAll(titleLabel);
        mainBox.getChildren().addAll(coverImageView);
        nameBox.getChildren().addAll(circleAvatar(momentsVO.getAvatar()), nameLabel, cloverVBox, commentVBox);
        nameBox.setSpacing(10);
        mainBox.setId(String.valueOf(momentsVO.getId()));
        mainBox.setOnMouseClicked((e) -> {
            openMomentDetail(Integer.parseInt(mainBox.getId()));
        });
        vBox.getChildren().addAll(titleBox, mainBox, nameBox);

        rootHBox.getChildren().add(vBox);
        VBox.setMargin(nameBox, new Insets(5, 0, 0, 0));
        HBox.setMargin(cloverVBox, new Insets(0, 0, 0, 100));
        HBox.setMargin(vBox, new Insets(0, 10, 0, 10));
//        HBox.setMargin(label, new Insets(0, 0 , 0, 10));
    }

    private void openMomentDetail(int momentId) {
        try {
            FXMLLoader momentDetailLoader = new FXMLLoader(MomentsApplication.class.getResource("moment-detail.fxml"));
            Parent momentDetailRoot = momentDetailLoader.load();
            MomentDetailController momentDetailController = momentDetailLoader.getController();
            momentDetailController.initializeData(this.userId, momentId);
            Stage stage = new Stage();
            Scene scene = new Scene(momentDetailRoot, 800, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void thumbUpOrDown(Label label) {
        int likes = Integer.parseInt(label.getText());
        boolean isThumbUp = momentsService.isThumbUp(userId, Integer.parseInt(label.getId()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        if (isThumbUp) {
            alert.setContentText("Thumb up successfully!");
            label.setText(String.valueOf(likes + 1));
        } else {
            alert.setContentText("You dislike this post");
            label.setText(String.valueOf(likes - 1));
        }
        alert.showAndWait();
    }

    private ImageView circleAvatar(String url) {
        Image image = new Image(MomentsApplication.class.getResource(url).toString(), 40, 40, true, true);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        int arc = 40;
        Rectangle clip = new Rectangle(imageView.getFitWidth(), imageView.getFitHeight());
        clip.setArcWidth(arc);
        clip.setArcHeight(arc);
        imageView.setClip(clip);
        return imageView;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
