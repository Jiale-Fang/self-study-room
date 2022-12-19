package com.example.demo.controller;

import com.example.demo.*;
import com.example.demo.constant.CommonConstant;
import com.example.demo.constant.MessageTypeConstant;
import com.example.demo.dao.StudyRoomDao;
import com.example.demo.entity.Message;
import com.example.demo.pojo.Seat;
import com.example.demo.pojo.User;
import com.example.demo.service.StudyRoomService;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.StudyRoomServiceImpl;
import com.example.demo.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.*;

public class StudyRoomController implements Initializable {

    private int userId;
    private String gender;
    private String avatar;
    private boolean isSitDown;
    private ObjectOutputStream oos;
    private ChatController chatController;
    private FXMLLoader userInfoCardFxml;
    private Parent chatRoot;
    private Scene chatScene;
    private Scene userInfoCardScene = null;

    private static final UserService userService = new UserServiceImpl();

    private static Map<Integer, Button> btnMap = new HashMap<>();

    @FXML
    private Pane studyRoomPane;
    @FXML
    private ImageView avatarImg;
    @FXML
    private Label nameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            //Initialize seats
            initializeSeats();
            userInfoCardFxml = new FXMLLoader(UserInfoCardApplication.class.getResource("user-info-card.fxml"));
            Parent userInfoCardRoot = userInfoCardFxml.load();
            userInfoCardScene = new Scene(userInfoCardRoot, 350, 500);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initializeData(int userId) {
        this.userId = userId;
        User user = userService.getUserInfo(userId);
        this.gender = user.getGender();
        this.avatar = user.getAvatar();
        avatarImg.setImage(new Image(StudyRoomApplication.class.getResource(this.avatar).toString(), 45, 45, true, true));
        int arc = 45;
        Rectangle clip = new Rectangle(avatarImg.getFitWidth(), avatarImg.getFitHeight());
        clip.setArcWidth(arc);
        clip.setArcHeight(arc);
        avatarImg.setClip(clip);
        nameLabel.setText(user.getUsername());
    }

    private void initializeSeats() {
        btnMap.put(1, seatBtn1);
        btnMap.put(2, seatBtn2);
        btnMap.put(3, seatBtn3);
        btnMap.put(4, seatBtn4);
        btnMap.put(5, seatBtn5);
        btnMap.put(6, seatBtn6);
        btnMap.put(7, seatBtn7);
        btnMap.put(8, seatBtn8);
        btnMap.put(9, seatBtn9);
        btnMap.put(10, seatBtn10);
        btnMap.put(11, seatBtn11);
        btnMap.put(12, seatBtn12);
        btnMap.put(13, seatBtn13);
        btnMap.put(14, seatBtn14);
        btnMap.put(15, seatBtn15);
        btnMap.put(16, seatBtn16);
        btnMap.put(17, seatBtn17);
        btnMap.put(18, seatBtn18);
//        List<Seat> seatList = studyRoomDao.getAllSeats();
    }

    @FXML
    private void openMoments() {
        try {
            FXMLLoader momentsFxmlLoader = new FXMLLoader(MomentsApplication.class.getResource("moments-view.fxml"));
            Parent root = momentsFxmlLoader.load();
            MomentsController momentsController = momentsFxmlLoader.getController();
            Scene scene = new Scene(root, 1100, 800);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            momentsController.setUserId(this.userId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void openChatRoom() {
        try {
            Stage stage = new Stage();
            stage.setScene(chatScene);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void openUserInfoCard(Integer friendId) {
        Stage stage = new Stage();
        stage.setTitle("User Information Card");
        stage.setScene(userInfoCardScene);
        stage.show();
        UserInfoCardController userInfoCardController = userInfoCardFxml.getController();
        userInfoCardController.initializeData(chatController, this.chatScene, this.userId, friendId);
    }

    private void sitOrStand(Button button) {
        Seat seat = (Seat) button.getUserData();
        if (seat.isSitDown()) {
            if (seat.getUserId() != this.userId) {
                openUserInfoCard(seat.getUserId());
            } else {
                standUp(button);
            }
        } else {
            if (!this.isSitDown) {
                sitDown(button);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("Ooops, it seems like you have already sit down in a seat");
                alert.showAndWait();
            }
        }
    }

    private void sitDown(Button button) {
        Seat seat = (Seat) button.getUserData();
        seat.setSitDown(true);
        seat.setUserId(this.userId);
        seat.setGender(gender);
        button.setUserData(seat);
        String[] split = button.getId().split("seatBtn");
        seat.setSeatId(Integer.parseInt(split[1]));
        sitDownBtn(button);
        Message message = new Message(MessageTypeConstant.ONE_SEAT_INFO, seat);
        message.setSenderId(this.userId);
        this.isSitDown = true;
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void standUp(Button button) {
        standUpBtn(button);
        Seat seat = (Seat) button.getUserData();
        seat.setSitDown(false);
        button.setUserData(seat);
        System.out.println(button.getId() + "Stand up");
        String[] split = button.getId().split("seatBtn");
        seat.setSeatId(Integer.parseInt(split[1]));
        Message message = new Message(MessageTypeConstant.ONE_SEAT_INFO, seat);
        message.setSenderId(this.userId);
        this.isSitDown = false;
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        studyRoomService.finishTask(new Task(), seat);
    }

    private void sitDownBtn(Button button) {
        Seat seat = (Seat) button.getUserData();
        Image image = seat.getGender().equals(CommonConstant.MALE) ?
                new Image(String.valueOf(StudyRoomApplication.class.getResource("images/studyroom/boy.png")))
                : new Image(String.valueOf(StudyRoomApplication.class.getResource("images/studyroom/girl.png")));
        BackgroundSize backgroundSize = new BackgroundSize(60.0, 60.0, true, true, true, true);
        Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize));
        button.setBackground(background);
        button.setStyle("-fx-cursor: HAND; -fx-opacity: 1.0");
    }

    /**
     * When a user logs in, this method helps to initialize all seats info
     *
     * @param message Data packet
     */
    public void updateAllSeatsInfo(Message message) {
        Map<Integer, Seat> seatMap = (Map<Integer, Seat>) message.getObject();
        System.out.println("seat map: " + seatMap);
        for (int i = 1; i <= btnMap.size(); i++) {
            Button button = btnMap.get(i);
            Seat seat = seatMap.get(i);
            button.setUserData(seat);
            if (seat.isSitDown()) sitDownBtn(button);
            else standUpBtn(button);
            button.setUserData(seat);
        }
    }

    /**
     * When other users sit down or stand up, update our own seat info
     *
     * @param message Data packet
     */
    public void updateOneSeat(Message message) {
        Seat seat = (Seat) message.getObject();
        Button button = btnMap.get(seat.getSeatId());
        button.setUserData(seat);
        if (seat.isSitDown()) sitDownBtn(button);
        else standUpBtn(button);
        btnMap.put(seat.getSeatId(), button);
    }

    private void standUpBtn(Button button) {
        button.setStyle("-fx-cursor: HAND; -fx-opacity: 0.0");
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void initializeChat(Parent chatRoot) {
        this.chatRoot = chatRoot;
        this.chatScene = new Scene(chatRoot, 1200, 700);
    }

    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @FXML
    private Button seatBtn1;
    @FXML
    private Button seatBtn2;
    @FXML
    private Button seatBtn3;
    @FXML
    private Button seatBtn4;
    @FXML
    private Button seatBtn5;
    @FXML
    private Button seatBtn6;
    @FXML
    private Button seatBtn7;
    @FXML
    private Button seatBtn8;
    @FXML
    private Button seatBtn9;
    @FXML
    private Button seatBtn10;
    @FXML
    private Button seatBtn11;
    @FXML
    private Button seatBtn12;

    @FXML
    private Button seatBtn13;
    @FXML
    private Button seatBtn14;
    @FXML
    private Button seatBtn15;
    @FXML
    private Button seatBtn16;
    @FXML
    private Button seatBtn17;
    @FXML
    private Button seatBtn18;

    @FXML
    protected void onSeatButtonClick1() {
        sitOrStand(seatBtn1);
    }

    @FXML
    protected void onSeatButtonClick2() {
        sitOrStand(seatBtn2);
    }

    @FXML
    protected void onSeatButtonClick3() {
        sitOrStand(seatBtn3);
    }

    @FXML
    protected void onSeatButtonClick4() {
        sitOrStand(seatBtn4);
    }

    @FXML
    protected void onSeatButtonClick5() {
        sitOrStand(seatBtn5);
    }

    @FXML
    protected void onSeatButtonClick6() {
        sitOrStand(seatBtn6);
    }

    @FXML
    protected void onSeatButtonClick7() {
        sitOrStand(seatBtn7);
    }

    @FXML
    protected void onSeatButtonClick8() {
        sitOrStand(seatBtn8);
    }

    @FXML
    protected void onSeatButtonClick9() {
        sitOrStand(seatBtn9);
    }

    @FXML
    protected void onSeatButtonClick10() {
        sitOrStand(seatBtn10);
    }

    @FXML
    protected void onSeatButtonClick11() {
        sitOrStand(seatBtn11);
    }

    @FXML
    protected void onSeatButtonClick12() {
        sitOrStand(seatBtn12);
    }
    @FXML
    protected void onSeatButtonClick13() {
        sitOrStand(seatBtn13);
    }

    @FXML
    protected void onSeatButtonClick14() {
        sitOrStand(seatBtn14);
    }

    @FXML
    protected void onSeatButtonClick15() {
        sitOrStand(seatBtn15);
    }

    @FXML
    protected void onSeatButtonClick16() {
        sitOrStand(seatBtn16);
    }

    @FXML
    protected void onSeatButtonClick17() {
        sitOrStand(seatBtn17);
    }

    @FXML
    protected void onSeatButtonClick18() {
        sitOrStand(seatBtn18);
    }

    public void beenRemoved() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText(this.userId + "Ooops, it seems like you have not operated for a long time. You have to login again.");
        alert.showAndWait();
        Stage stage = (Stage) studyRoomPane.getScene().getWindow();
        stage.close();
    }
}