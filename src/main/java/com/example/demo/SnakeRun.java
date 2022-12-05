package com.example.demo;

import com.example.demo.snake.Food;
import com.example.demo.snake.Snake;
import com.example.demo.snake.SnakePart;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class SnakeRun extends Application {
    private static final int multiple = 22;

    private static final int width = SnakePart.DEF_LEN * (multiple + 10);

    private static final int height = SnakePart.DEF_LEN * multiple;

    private double windowXOffset = 0;

    private double windowYOffset = 0;

    private final ArrayBlockingQueue<Food> foodQueue = new ArrayBlockingQueue<>(1);

//    private MediaPlayer mediaPlayer;

    private AnchorPane pane;

    private Canvas canvas;

    private boolean stop;

    @Override
    public void start(Stage stage) throws Exception {
        pane = new AnchorPane();
        canvas = new Canvas(width, height);
        Snake snake = new Snake(SnakePart.DEF_LEN * 6, SnakePart.DEF_LEN * 6, 5, 10, canvas, foodQueue);
        //监听死亡事件
        snake.setSnakeEvent(type -> alert(type, stage));
        snake.init();
        pane.getChildren().add(canvas);
        pane.setOnKeyPressed(snake::turn);
//        pane.setBackground(getBackground());

        //鼠标按下 记录位置
        pane.setOnMousePressed(event -> {
            windowXOffset = event.getSceneX();
            windowYOffset = event.getSceneY();
        });
        //鼠标拖动 移动stage窗口
        pane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - windowXOffset);
            stage.setY(event.getScreenY() - windowYOffset);
        });

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.setTitle("贪吃蛇");
        //隐藏窗口标题和操作
        stage.initStyle(StageStyle.UNDECORATED);
        //退出事件
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
        //获取窗口焦点
        canvas.requestFocus();
        //初始化并播放音乐
//        initMedia();
        //开启食物生成线程
        genFood(snake, canvas);
        //运行蛇
        snake.run();


    }

    private Background getBackground() {
        BackgroundImage bImg = new BackgroundImage(
                new Image(this.getClass().getResourceAsStream("/com/example/fx/bj11.jpg")),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(-1, -1, true, true, false, true));
        return new Background(bImg);
    }

//    private void initMedia() {
//        Media media = new Media(this.getClass().getResource("/com/example/fx/dizi.mp3").toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setCycleCount(Integer.MAX_VALUE);
//        mediaPlayer.play();
//    }

    private void genFood(Snake snake, Canvas canvas) {
        new Thread(() -> {
            Random random = new Random();
            while (!stop) {
                int x = random.nextInt(multiple - 1);
                int y = random.nextInt(multiple - 1);
                for (SnakePart snakePart : snake.getHeadAndBody()) {
                    if (snakePart.getX() == x && snakePart.getY() == y) {
                        x = random.nextInt(multiple - 1);
                        y = random.nextInt(multiple - 1);
                    }
                }
                try {
                    Food food = new Food(x * SnakePart.DEF_LEN, y * SnakePart.DEF_LEN, canvas.getGraphicsContext2D());
                    foodQueue.put(food);
                    if (!stop) {
                        Platform.runLater(food::show);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    private void alert(Snake.EventType eventType, Stage stage) {
        if (eventType == Snake.EventType.DIE) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Dead Confirm");
            alert.setHeaderText("Game over");
            alert.setContentText("Your Grade:" + eventType.getData().getScore() + "Point");
            ButtonType buttonTypeOne = new ButtonType("Start");
            ButtonType buttonTypeTwo = new ButtonType("Close");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                eventType.getData().getSnake().clearAll();
                Snake snake = new Snake(SnakePart.DEF_LEN * 6, SnakePart.DEF_LEN * 6, 5, 10, canvas, foodQueue);
                snake.setSnakeEvent(type -> alert(type, stage));
                pane.setOnKeyPressed(snake::turn);
                snake.init();
                snake.run();
            } else if (result.get() == buttonTypeTwo) {
                stop = true;
//                mediaPlayer.stop();
                eventType.getData().getSnake().clearAll();
                stage.close();
            } else {
                alert.close();
            }
        }

    }
}
