package com.example.demo.snake;

import com.example.demo.SnakeRun;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class SnakeHead extends SnakePart {
    public SnakeHead(int x, int y, GraphicsContext graphics) {
        super(x, y, graphics);
    }

    public SnakeHead(int x, int y, Color color, GraphicsContext graphics) {
        super(x, y, color, graphics);
    }

    public SnakeHead(int x, int y, int len, Color color, GraphicsContext graphics) {
        super(x, y, len, color, graphics);
    }

    @Override
    public SnakePart move(int dX, int dY) {
        x += dX;
        y += dY;
//        graphics.setFill(color);
        Image image = new Image(SnakeRun.class.getResource("images/snake/snake.jpeg").toString());
        graphics.drawImage(image, x, y, len, len);
        return this;
    }

    @Override
    public SnakePart updateLocation(int pX, int pY) {
        x = pX;
        y = pY;
//        graphics.setFill(color);
        Image image = new Image(SnakeRun.class.getResource("images/snake/snake.jpeg").toString());
        graphics.drawImage(image, x, y, len, len);
        return this;
    }
}

