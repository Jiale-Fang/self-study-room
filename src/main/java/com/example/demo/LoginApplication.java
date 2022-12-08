package com.example.demo;

import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginApplication extends Application {

    UserService user = new UserServiceImpl();
    Stage SignStage = new Stage();
    GridPane SignPane = new GridPane();// 登录界面的
    Scene SignScene = new Scene(SignPane, 600, 400);
    GridPane CenterPane = new GridPane();// 主程序的

    Scene CenterScene = new Scene(CenterPane, 600, 600);
    GridPane RegisterPane = new GridPane();// 注册界面的
    Scene RegisterScene = new Scene(RegisterPane, 600, 400);
    String backgroundUrl = LoginApplication.class.getResource("images/snake/background.jpeg").toString();
    int id = 0;

    public void start(Stage primaryStage) {// UI主轴
        Sign();
//        Center();
//        Register();
        SignStage.setTitle("Self-Study");
        SignStage.setScene(SignScene);

        SignStage.show();
    }

    void Sign() {// ***登录UI
        Label SignTitle = new Label("    Login");
        SignTitle.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 40));
        Label SignAccountLabel = new Label("Account:");
        SignAccountLabel.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 20));
        Label SignPasswordLabel = new Label("Password:");
        SignPasswordLabel.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 20));
        Label SignUnableLabel = new Label("Can't login");
        SignUnableLabel.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 15));
        Label SignForgetLabel = new Label("Account or password wrong！");
        SignForgetLabel.setTextFill(Color.RED);
        SignForgetLabel.setFont(Font.font("T", FontPosture.ITALIC, 20));
        SignForgetLabel.setVisible(false);

        TextField SignAccountTextField = new TextField();
        TextField SignPasswordTextField = new TextField();

        Button SignOkButton = new Button("Login");
        Button SignRegisterButton = new Button("Register");
        Button SignForgetButton = new Button("Forget password");
        SignOkButton.setTranslateX(250);
        SignOkButton.setTranslateY(200);
        SignForgetButton.setTranslateX(420);
        SignForgetButton.setTranslateY(220);
        SignRegisterButton.setTranslateX(420);
        SignRegisterButton.setTranslateY(250);

        SignPane.setHgap(10);
        SignPane.setVgap(10);

        SignPane.setPadding(new Insets(0, 10, 10, 10));

        SignPane.add(SignTitle, 4, 1);
        SignPane.add(SignAccountLabel, 0, 3);
        SignPane.add(SignAccountTextField, 4, 3);
        SignPane.add(SignPasswordLabel, 0, 4);
        SignPane.add(SignPasswordTextField, 4, 4);
        SignPane.add(SignUnableLabel, 5, 6);
        SignPane.add(SignForgetLabel, 4, 9);
        SignPane.getChildren().addAll(SignOkButton, SignRegisterButton, SignForgetButton);
        SignPane.setStyle("-fx-background-image: url(" + backgroundUrl + "); "
                + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;"
                + "-fx-background-color:  transparent;" + "-fx-background-size: 750px;");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        SignOkButton.setOnAction(e -> { // 转跳主程序界面
            if (user.login(SignAccountTextField.getText(), SignPasswordTextField.getText())) {
                id = user.getId(SignAccountTextField.getText());
//                Center();
                try {
                    StudyRoomApplication studyRoomApplication = new StudyRoomApplication();
                    studyRoomApplication.setUserId(id);
                    studyRoomApplication.start(new Stage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                SignStage.setScene(CenterScene);
                SignStage.close();
            } else {
                alert.setContentText("Account or Password is Wrong !!!");
                alert.show();
            }
        });

        SignRegisterButton.setOnAction(e -> {// 转跳注册界面
            Register();
            SignStage.setScene(RegisterScene);
        });

    }

    void Register() {// ***注册UI
        Label RegisterTitle = new Label("Register");
        RegisterTitle.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 30));
        Label RegisterAccountLabel = new Label(" Account:");
        RegisterAccountLabel.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 20));
        Label RegisterPasswordLabel = new Label("Password:");
        RegisterPasswordLabel.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 20));
        Label RegisterIDLabel = new Label("Username:");
        RegisterIDLabel.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 20));
        Label RegisterRepeatLabel = new Label("The account is already existed");
        RegisterRepeatLabel.setTextFill(Color.RED);
        RegisterRepeatLabel.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 20));
        RegisterRepeatLabel.setVisible(false);
        Label RegisterSuccessLabel = new Label("Register successfully！");
        RegisterSuccessLabel.setTextFill(Color.RED);
        RegisterSuccessLabel.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 40));
        RegisterSuccessLabel.setVisible(false);

        Button RegisterOkButton = new Button("Register");
        RegisterOkButton.setTranslateX(200);
        RegisterOkButton.setTranslateY(260);
        Button RegisterReButton = new Button("Go back");
        RegisterReButton.setTranslateX(300);
        RegisterReButton.setTranslateY(260);

        TextField RegisterAccountTextField = new TextField();
        TextField RegisterPasswordTextField = new TextField();
        TextField RegisterIDTextField = new TextField();

        RegisterPane.setHgap(10);
        RegisterPane.setVgap(10);
        RegisterPane.setStyle("-fx-background-image: url(" + backgroundUrl
                + "); " + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;"
                + "-fx-background-color:  transparent;" + "-fx-background-size: 750px;");

        RegisterPane.setPadding(new Insets(0, 10, 10, 10));
        RegisterPane.add(RegisterTitle, 4, 1);
        RegisterPane.add(RegisterAccountLabel, 3, 5);
        RegisterPane.add(RegisterPasswordLabel, 3, 7);
        RegisterPane.add(RegisterIDLabel, 3, 9);
        RegisterPane.add(RegisterAccountTextField, 4, 5);
        RegisterPane.add(RegisterPasswordTextField, 4, 7);
        RegisterPane.add(RegisterIDTextField, 4, 9);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        RegisterPane.getChildren().addAll(RegisterOkButton, RegisterReButton);

        RegisterReButton.setOnAction(e -> SignStage.setScene(SignScene));

        RegisterOkButton.setOnAction(e -> {
            if (RegisterAccountLabel.getText() == "" | RegisterPasswordTextField.getText() == "" | RegisterIDTextField.getText() == "") {
                alert.setContentText("Please input all the information");
                alert.show();
            }
            User u = new User(RegisterAccountTextField.getText(), RegisterPasswordTextField.getText(), RegisterIDTextField.getText());
            if (user.exist(RegisterAccountTextField.getText())) {
                user.insert(u);
                alert.setContentText("Successfully");
                alert.show();
                user.getId(u.getAccount());
//                Center();
//                StudyRoomApplication studyRoomApplication = new StudyRoomApplication();
//                studyRoomApplication.setUserId(userId);
//                try {
//                    studyRoomApplication.start(new Stage());
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }
//                SignStage.setScene(CenterScene);
            } else
                alert.setContentText("The account has been existed！");
            alert.show();
        });
    }

    void Center() {// ***主体UI
        CenterPane.setHgap(100);
        CenterPane.setVgap(30);
        Label Title = new Label("Information");
        Title.setFont(Font.font("T", FontWeight.LIGHT, FontPosture.ITALIC, 20));
        Label account = new Label("account");
        Label password = new Label("password");
        Label age = new Label("age");
        Label gender = new Label("gender");
        Label phone = new Label("phone");
//        Label birthday = new Label("birthday");
        Label username = new Label("username");
//        Image image1 = new Image("");
//        Image image2 = new Image("");
        TextField AccountTextField = new TextField(user.show(id).getAccount());
        TextField PasswordTextField = new TextField(user.show(id).getPassword());
        TextField ageTextField = new TextField(String.valueOf(user.show(id).getAge()));
        TextField genderTextField = new TextField(user.show(id).getGender());
        TextField phoneTextField = new TextField(user.show(id).getPhone());
//        TextField birthdayTextField = new TextField(String.valueOf(user.show(id).getBirthday()));
        TextField usernameTextField = new TextField(user.show(id).getUsername());
        Button ConfirmButton = new Button("Confirm");
        Button Logout = new Button("Logout");
        CenterPane.add(Title, 2, 0);
        CenterPane.add(username, 1, 1);
        CenterPane.add(usernameTextField, 2, 1);
        CenterPane.add(account, 1, 2);
        CenterPane.add(AccountTextField, 2, 2);
        CenterPane.add(password, 1, 3);
        CenterPane.add(PasswordTextField, 2, 3);
        CenterPane.add(age, 1, 4);
        CenterPane.add(ageTextField, 2, 4);
        CenterPane.add(gender, 1, 5);
        CenterPane.add(genderTextField, 2, 5);
        CenterPane.add(phone, 1, 6);
        CenterPane.add(phoneTextField, 2, 6);
//        CenterPane.add(birthday,1,7);
//        CenterPane.add(birthdayTextField,2,7);
        CenterPane.add(ConfirmButton, 2, 9);
        CenterPane.add(Logout, 2, 10);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        CenterPane.setStyle("-fx-background-image: url(" + backgroundUrl
                + "); " + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;"
                + "-fx-background-color:  transparent;" + "-fx-background-size: 1200px;");
        User u = new User(AccountTextField.getText(), PasswordTextField.getText(), Integer.parseInt(ageTextField.getText()), genderTextField.getText(), phoneTextField.getText(), usernameTextField.getText());
        ConfirmButton.setOnAction(e -> {

            String a = AccountTextField.getText();
            if (user.exist(AccountTextField.getText()) | a.equals(u.getAccount())) {
                User u1 = new User(id, AccountTextField.getText(), PasswordTextField.getText(), Integer.parseInt(ageTextField.getText()), genderTextField.getText(), phoneTextField.getText(), usernameTextField.getText());
                user.update(u1);
                alert.setContentText("Successfully");
                alert.show();

            } else
                alert.setContentText("The account has been existed！");
            alert.show();
        });
        Logout.setOnAction(e -> {
            SignStage.setScene(SignScene);
            id = 0;
        });

    }

    public void openCenter(int userId){
        this.id = userId;
        SignStage.setTitle("Self-Study");
        SignStage.setScene(SignScene);
        SignStage.show();
        Center();
        SignStage.setScene(CenterScene);
    }

    public static void main(String[] args) {// ***启动
        launch(args);
    }

}