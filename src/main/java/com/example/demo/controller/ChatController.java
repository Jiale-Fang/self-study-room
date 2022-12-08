package com.example.demo.controller;

import com.example.demo.ChatApplication;
import com.example.demo.constant.CommonConstant;
import com.example.demo.entity.Message;
import com.example.demo.pojo.ChatLog;
import com.example.demo.pojo.User;
import com.example.demo.service.ChatService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.ChatServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.vo.ContactVO;
import com.example.demo.vo.ChatVO;
import com.example.demo.vo.ChatCardVO;
import com.example.demo.vo.UserChatVO;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Objects;

public class ChatController {

    private int userId;
    //My avatar
    private String avatar;
    private String username;
    //-1 means you are in the group chat
    private int friendId = CommonConstant.GROUP_CHAT_ID;
    private ObjectOutputStream oos;
    private final static ChatService chatService = new ChatServiceImpl();
    private final static UserService userService = new UserServiceImpl();

    @FXML
    private BorderPane rootLayout;
    @FXML
    private VBox chatArea;
    @FXML
    private TextField textField;
    @FXML
    private ScrollPane chatScrollPane;
    @FXML
    private VBox allContactBox;
    @FXML
    private ImageView slideBarAvatar;
    @FXML
    private Label groupChatTimeLabel;
    @FXML
    private Label groupChatContentLabel;

    public void initializeData(int userId) {
        this.userId = userId;
        User user = userService.getUserInfo(userId);
        this.avatar = user.getAvatar();
        this.username = user.getUsername();
        initializeGroupChatCard();
        initializeContactCard(this.userId);
        changeToGroupChat();
        slideBarAvatar.setImage(new Image(ChatApplication.class.getResource(this.avatar).toString(), 50, 50, true, true));
    }

    private void initializeGroupChatCard(){
        ChatCardVO chatCardVO = chatService.getGroupChatCardInfo();
        groupChatContentLabel.setText(chatCardVO.getContent());
        groupChatTimeLabel.setText(chatCardVO.getFormatTime());
    }

    private void initializeContactCard(int userId) {
        List<ContactVO> contactVOList = userService.getContactInfo(userId);
        for (ContactVO contactVO : contactVOList) {
            addContactBox(contactVO);
        }
    }

    public void addContactBox(ContactVO contactVO){
        HBox contactBox = new HBox();
        contactBox.setId(String.valueOf(contactVO.getFriendId()));
        contactBox.setStyle("-fx-background-color: #19191A; -fx-spacing: 5; -fx-cursor: HAND;");
        contactBox.setAlignment(Pos.CENTER_LEFT);
        contactBox.setPrefSize(216, 80);
        ImageView avatarImg = new ImageView(new Image(String.valueOf(ChatApplication.class.getResource(contactVO.getAvatar())), 65, 65, true, true));

        VBox textBox = new VBox();
        textBox.setPrefSize(142, 80);
        textBox.setAlignment(Pos.CENTER_LEFT);
        textBox.setStyle("-fx-background-color: #19191A; -fx-spacing: 5;");
        Label nameLabel = new Label();
        Label contentLabel = new Label();
        nameLabel.setWrapText(true);
        contentLabel.setWrapText(true);
        nameLabel.setText(contactVO.getUsername());
        contentLabel.setText(contactVO.getContent());
        nameLabel.setTextFill(Color.valueOf("#fafcff"));
        nameLabel.setFont(Font.font("Arial Black", 16.0));
        contentLabel.setTextFill(Color.valueOf("#c7c8c9"));
        contentLabel.setFont(Font.font("System Bold", 13.0));
        textBox.getChildren().addAll(nameLabel, contentLabel);

        VBox timeBox = new VBox();
        timeBox.setPrefSize(73, 80);
        Label timeLabel = new Label();
        VBox.setMargin(timeLabel, new Insets(7.5, 10, 0, 0));
        timeLabel.setMinWidth(68);
        timeLabel.setTextFill(Color.valueOf("#c7c8c9"));
        timeLabel.setFont(Font.font("System Bold", 13.0));
        timeLabel.setText(contactVO.getFormatTime());
        timeBox.getChildren().add(timeLabel);

        contactBox.getChildren().addAll(avatarImg, textBox, timeBox);
        contactBox.setOnMouseClicked((e) -> {
            changeToFriend(Integer.parseInt(contactBox.getId()));
        });
        allContactBox.getChildren().add(contactBox);
    }

    private void changeToFriend(int friendId) {
        this.friendId = friendId;
        List<ChatVO> privateChatMessage = chatService.getPrivateChatMessage(this.userId, this.friendId);
        chatArea.getChildren().clear();
        for (ChatVO chatVO : privateChatMessage) {
            appendMessage(chatVO.getSender() == this.userId, chatVO.getContent(), chatVO.getAvatar(), chatVO.getSenderName());
        }
        chatScrollPane.setVvalue(1.0);
    }

    @FXML
    private void changeToGroupChat() {
        List<ChatVO> groupChatMessage = chatService.getGroupChatMessage();
        this.friendId = CommonConstant.GROUP_CHAT_ID;
        chatArea.getChildren().clear();
        for (ChatVO chatVO : groupChatMessage) {
            appendMessage(chatVO.getSender() == this.userId, chatVO.getContent(), chatVO.getAvatar(), chatVO.getSenderName());
        }
        chatScrollPane.setVvalue(1.0);
    }

    @FXML
    protected void sendMessage() {
        try {
            String text = textField.getText();
            if (Objects.isNull(text) || text.length() == 0) return;
            appendMessage(true, text, this.avatar, this.username);

            int mesType;
            //send to group
            if (this.friendId == CommonConstant.GROUP_CHAT_ID) mesType = CommonConstant.GROUP_CHAT_TEXT_TYPE;
            else mesType = CommonConstant.PRIVATE_CHAT_TEXT_TYPE;

            UserChatVO userChatVO = new UserChatVO(this.userId, this.friendId, this.username, this.avatar, text);
            Message messageToSend = new Message(mesType, userChatVO);
            messageToSend.setSenderId(this.userId);
            oos.writeObject(messageToSend);
            textField.setText("");
            ChatLog chatLog = new ChatLog(this.userId, text);
            if (friendId == CommonConstant.GROUP_CHAT_ID) {
                chatLog.setTextType(CommonConstant.GROUP_CHAT_TEXT_TYPE);
            } else {
                chatLog.setReceiver(this.friendId);
                chatLog.setTextType(CommonConstant.PRIVATE_CHAT_TEXT_TYPE);
            }
            chatService.saveChatLog(chatLog);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onKeyPressedTextArea(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            this.sendMessage();
        }
    }

    @FXML
    private void onSignOut() {
        Stage stage = (Stage) rootLayout.getScene().getWindow();
        stage.setScene(null);
        stage.close();
    }

    public void receiveMessage(Message message) {
        //When you are talking to your friends, append this message
        if (message.getSenderId() == this.friendId) {
            UserChatVO userChatVO = (UserChatVO) message.getObject();
            appendMessage(false, userChatVO.getContent(), userChatVO.getAvatar(), userChatVO.getUsername());
            chatScrollPane.setVvalue(1.0);
        }
    }

    public void receiveGroupMessage(Message message) {
        //May receive message from others or our own, so we need to judge
        if (!message.getSenderId().equals(this.userId)) {
            UserChatVO userChatVO = (UserChatVO) message.getObject();
            appendMessage(false, userChatVO.getContent(), userChatVO.getAvatar(), userChatVO.getUsername());
        }
    }

    public void appendMessage(boolean isMyMessage, String text, String avatar, String sendName) {
        if (Objects.isNull(text) || text.length() == 0) return;
        Label label = generateMessageBubble(isMyMessage, text);
//        HBox.setMargin(label, new Insets(0, 0, 0, 0));
        //Generate small triangle for the message bubble
        double[] points;
        points = isMyMessage ? new double[]{0.0, 5.0, 10.0, 0.0, 10.0, 10.0} : new double[]{0.0, 0.0, 0.0, 10.0, 10.0, 5.0};
        Polygon triangle = new Polygon(points);
        triangle.setFill(Color.rgb(179, 231, 244));
        //Hold avatar, message label
        HBox messageBox = new HBox();
        messageBox.setPrefWidth(350);
        messageBox.setPadding(new Insets(10, 0, 0, 0));
        Label nameLabel = new Label();
        nameLabel.setTextFill(Color.GRAY);
        nameLabel.setWrapText(true);
        //hold user's message bubble and name
        HBox hBox = new HBox();
        //hold user's message bubble
        VBox vBox = new VBox();
        if (isMyMessage) {
            VBox.setMargin(nameLabel, new Insets(0, 5, 0, 0));
            hBox.getChildren().addAll(label, triangle);
            vBox.getChildren().addAll(nameLabel, hBox);
//            vBox.setAlignment(Pos.TOP_RIGHT);
            HBox.setMargin(triangle, new Insets(5, 0, 0, 0));
            messageBox.getChildren().addAll(vBox, generateAvatar(true, avatar));
            messageBox.setAlignment(Pos.TOP_RIGHT);
        } else {
            nameLabel.setText(sendName);
            hBox.getChildren().addAll(triangle, label);
            VBox.setMargin(nameLabel, new Insets(0, 0, 0, 5));
            vBox.getChildren().addAll(nameLabel, hBox);
            HBox.setMargin(triangle, new Insets(5, 0, 0, 10));
            messageBox.getChildren().addAll(generateAvatar(false, avatar), vBox);
        }
        chatArea.getChildren().add(messageBox);
    }

    private Label generateMessageBubble(boolean isMyMessage, String text) {
        Label label = new Label();
        label.setMaxWidth(330);
        label.setWrapText(true);
        if (isMyMessage) {
            label.setTextFill(Color.BLACK);
            label.setStyle("-fx-background-color: #9eea6a; -fx-background-radius: 10px;");
        } else {
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-background-color: #2b2b2b; -fx-background-radius: 10px;");
        }

        label.setText(text);
        label.setPadding(new Insets(6));
        label.setFont(new Font(14));
        return label;
    }

    private ImageView generateAvatar(boolean isMyMessage, String avatar) {
        Image img;
        if (isMyMessage)
            img = new Image(Objects.requireNonNull(ChatApplication.class.getResource(avatar)).toString(), 40.0, 40.0, true, true);
        else
            img = new Image(Objects.requireNonNull(ChatApplication.class.getResource(avatar)).toString(), 40.0, 40.0, true, true);
        ImageView imageView = new ImageView(img);
        imageView.resize(40, 40);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        return imageView;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

}