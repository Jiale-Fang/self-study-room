package com.example.demo.controller;

import com.example.demo.UserInfoCardApplication;
import com.example.demo.constant.CommonConstant;
import com.example.demo.service.ChatService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.ChatServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.vo.ContactVO;
import com.example.demo.vo.UserCardVO;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UserInfoCardController {
    private int userId;
    private int friendId;
    private String friendAvatar;
    private String friendName;
    private Scene chatScene;
    private ChatController chatController;
    private static final UserService userService = new UserServiceImpl();
    private static final ChatService chatService = new ChatServiceImpl();
    @FXML
    private Label username;
    @FXML
    private Label createTime;
    @FXML
    private Label cumulativeStudyTime;
    @FXML
    private ImageView genderImage;
    @FXML
    private ImageView avatarImg;

    public void initializeData(ChatController chatController, Scene chatScene, int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
        this.chatScene = chatScene;
        this.chatController = chatController;
        UserCardVO userCardVO = userService.getUserCardInfo(friendId);
        this.friendAvatar = userCardVO.getAvatar();
        this.friendName = userCardVO.getUsername();
        username.setText(userCardVO.getUsername());
        createTime.setText(userCardVO.getCreateTime().toString());
        cumulativeStudyTime.setText(userCardVO.getCumulativeStudyTime().toString() + " minutes");
        if (userCardVO.getGender().equals(CommonConstant.MALE)) {
            genderImage.setImage(new Image(String.valueOf(UserInfoCardApplication.class.getResource("images/studyroom/male.png"))));
        } else {
            genderImage.setImage(new Image(String.valueOf(UserInfoCardApplication.class.getResource("images/studyroom/female.png"))));
        }
        avatarImg.setImage(new Image(UserInfoCardApplication.class.getResource(userCardVO.getAvatar()).toString(), 150, 150, true, true));
    }

    @FXML
    private void openPrivateChat() {
        try {
            boolean flag = chatService.hasTalkedBefore(this.userId, this.friendId);
            if (!flag) {
                ContactVO contactVO = new ContactVO();
                contactVO.setFriendId(this.friendId);
                contactVO.setUsername(this.friendName);
                contactVO.setAvatar(friendAvatar);
                chatController.addContactBox(contactVO);
            }

            Stage stage = new Stage();
            stage.setScene(chatScene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}