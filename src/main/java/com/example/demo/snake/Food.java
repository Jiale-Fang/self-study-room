package com.example.demo.snake;

import com.example.demo.SnakeRun;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

public class Food {
    private final int x;
    private final int y;
    private final int size;
    private final Color color;
    private final GraphicsContext graphics;

    public Food(int x, int y, int size, Color color, GraphicsContext graphics) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
        this.graphics = graphics;
    }

    public Food(int x, int y, GraphicsContext graphics) {
        this.x = x;
        this.y = y;
        this.size = SnakePart.DEF_LEN;
        Random random = new Random();
        this.color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
        this.graphics = graphics;
    }

    public Food show() {
        graphics.setFill(color);
//        graphics.fillRect(x, y, size, size);
        Image image = new Image(SnakeRun.class.getResource("images/snake/snake.jpeg").toString());;
        graphics.drawImage(image, x, y, size, size);

        return this;
    }

    public void vanish() {
        graphics.clearRect(x, y, size, size);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }
}
