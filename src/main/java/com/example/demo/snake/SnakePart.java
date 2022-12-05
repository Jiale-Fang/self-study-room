package com.example.demo.snake;

import com.example.demo.SnakeRun;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * 身体片段
 */
public class SnakePart {
    public static final int DEF_LEN = 40;
    protected int x;
    protected int y;
    protected final int len;
    protected final Color color;
    protected final GraphicsContext graphics;

    public SnakePart(int x, int y, GraphicsContext graphics) {
        this.x = x;
        this.y = y;
        this.len = DEF_LEN;
        Random random = new Random();
        this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        this.graphics = graphics;
    }

    public SnakePart(int x, int y, Color color, GraphicsContext graphics) {
        this.x = x;
        this.y = y;
        this.len = DEF_LEN;
        this.color = color;
        this.graphics = graphics;
    }
    public SnakePart(int x, int y, int len, Color color, GraphicsContext graphics) {
        this.x = x;
        this.y = y;
        this.len = len;
        this.color = color;
        this.graphics = graphics;
    }

    public void remove() {
        graphics.clearRect(x, y, len, len);
    }

    public SnakePart move(int dX, int dY) {
        x += dX;
        y += dY;
        Image image = new Image(SnakeRun.class.getResource("images/snake/background.jpeg").toString());
        graphics.drawImage(image, x, y, len, len);
        return this;
    }
    public SnakePart updateLocation(int pX, int pY) {
        x = pX;
        y = pY;
        Image image = new Image(SnakeRun.class.getResource("images/snake/snake.jpeg").toString());
        graphics.drawImage(image, x, y, len, len);
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLen() {
        return len;
    }

    @Override
    public String toString() {
        return "SnakePart{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
