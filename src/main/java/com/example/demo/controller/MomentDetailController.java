package com.example.demo.controller;

import com.example.demo.MomentsApplication;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.User;
import com.example.demo.service.MomentsService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.MomentsServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.util.ChatLogTimeUtil;
import com.example.demo.vo.CommentVO;
import com.example.demo.vo.MomentDetailVO;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.LocalDateTime;
import java.util.List;

public class MomentDetailController {

    private int userId;
    private int authorId;
    private String authorAvatar;
    private String authorName;
    private String avatar;
    private String username;
    private int momentId;
    @FXML
    private Label titleLabel;
    @FXML
    private ImageView momentCover;
    @FXML
    private ImageView momentAvatar;
    @FXML
    private Label momentUsername;
    @FXML
    private Label momentTimeLabel;
    @FXML
    private TextField commentText;
    @FXML
    private VBox commentArea;

    private static final UserService userService = new UserServiceImpl();
    private static final MomentsService momentsService = new MomentsServiceImpl();

    @FXML
    private void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            this.sendComment();
        }
    }

    @FXML
    private void sendComment() {
        if (commentText.getText() == null || commentText.getText().length() == 0) {
            return;
        }
        LocalDateTime ldt = LocalDateTime.now();
        String formatTime = ChatLogTimeUtil.formatTime(ldt);
        addComment(this.username, formatTime, this.avatar, commentText.getText());
        Comment comment = new Comment(this.userId, this.momentId, commentText.getText());
        momentsService.addComment(comment);
        commentText.setText("");
    }

    private void addComment(String username, String formatTime, String avatar, String content) {
        HBox commentBox = new HBox();
        commentBox.setAlignment(Pos.CENTER_LEFT);
        commentBox.setMinHeight(72);
        commentBox.setPrefSize(402, 72);

        ImageView avatarImg = new ImageView(new Image(MomentsApplication.class.getResource(avatar).toString(), 50, 50, true, true));
        HBox.setMargin(avatarImg, new Insets(0, 0, 0, 10));
        VBox contentBox = new VBox();
        HBox.setMargin(contentBox, new Insets(0, 0, 0, 10));
        contentBox.setPrefSize(335, 72);
        contentBox.setAlignment(Pos.CENTER_LEFT);
        contentBox.setStyle("-fx-spacing: 5");

        HBox nameBox = new HBox();
        nameBox.setAlignment(Pos.BOTTOM_LEFT);
        nameBox.setSpacing(5);
        nameBox.setPrefSize(82, 21);
        Label nameLabel = new Label();
        nameLabel.setText(username);
        nameLabel.setTextFill(Color.valueOf("#9a6838"));
        nameLabel.setFont(Font.font("System Bold", 16.0));
        Label timeLabel = new Label();
        timeLabel.setText(formatTime);
        nameLabel.setTextFill(Color.valueOf("#9a6838"));
        timeLabel.setFont(Font.font("System", 13.0));
        timeLabel.setTextFill(Color.GRAY);
        nameBox.getChildren().addAll(nameLabel, timeLabel);

        Label contentLabel = new Label();
        contentLabel.setWrapText(true);
        contentLabel.setText(content);
        contentLabel.setFont(Font.font("Aria", 13.0));
        contentBox.getChildren().addAll(nameBox, contentLabel);

        commentBox.getChildren().addAll(avatarImg, contentBox);
        commentArea.getChildren().add(commentBox);
    }

    public void initializeData(int userId, int momentId) {
        User userInfo = userService.getUserInfo(userId);
        this.username = userInfo.getUsername();
        this.avatar = userInfo.getAvatar();
        this.userId = userId;
        this.momentId = momentId;
        MomentDetailVO momentDetailVO = momentsService.getMomentDetail(momentId);
        momentCover.setImage(new Image(MomentsApplication.class.getResource(momentDetailVO.getCover()).toString()));
        momentTimeLabel.setText(momentDetailVO.getFormatTime());
        momentUsername.setText(momentDetailVO.getUsername());
        momentAvatar.setImage(new Image(MomentsApplication.class.getResource(momentDetailVO.getAvatar()).toString()));
        titleLabel.setText(momentDetailVO.getTitle());
        titleLabel.setWrapText(true);
        this.authorAvatar = momentDetailVO.getAvatar();
        this.authorName = momentDetailVO.getUsername();
        this.authorId = momentDetailVO.getAuthorId();
        List<CommentVO> commentVOList = momentDetailVO.getCommentVOList();
        for (CommentVO commentVO : commentVOList) {
            System.out.println(commentVO);
            addComment(commentVO.getUsername(), commentVO.getFormatTime(), commentVO.getAvatar(), commentVO.getContent());
        }
    }
}
