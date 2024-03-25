package com.example.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Игра змейка.
 */
public class SnakeGame extends Application {
    protected static int speed = 5; // скорость.
    protected static final int width = 20; // ширина (в клеточках).
    protected static final int height = 20; // высота (в клеточках).
    protected static int foodX = 0; // координата еды X.
    protected static int foodY = 0; // координата еды Y.
    protected static final int cellSize = 25;  // размер клеточки.
    protected static final List<Cell> snake = new ArrayList<>();  // змейка.
    protected static Dir direction = Dir.left;  // направление.
    protected static boolean gameOver = false;  //флаг для конца игры.
    protected static boolean winGame = false;  //флаг для выигрыша.
    protected static final Random rand = new Random();  //рандом.


    /**
     * enum для направлений.
     */
    public enum Dir {
        left, right, up, down
    }

    /**
     * класс для клеточки.
     */
    public static class Cell {
        public int x;
        public int y;

        /**
         * Констурктор.
         */
        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    /**
     * Переопределяем из Aplication.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // получаем координаты еды.
            newFood();
            // создаём нужные вещи.
            VBox root = new VBox();
            Canvas c = new Canvas(width * cellSize, height * cellSize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);
            /*
              Класс необходимый для анимации.
             */
            new AnimationTimer() {
                private long lastTick = 0;

                /**
                 * Переписанный метод, now - время в наносекундах.
                 */
                @Override
                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        tick(gc);
                    }
                }

            }.start();

            Scene scene = new Scene(root, width * cellSize, height * cellSize);

            // управляем клавишами и устанавливаем направление.
            scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.A) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.S) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.D) {
                    direction = Dir.right;
                }

            });
            // добавляем первый кусок змейки.
            snake.add(new Cell(width / 2, height / 2));
            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Каждый кадр игры.
     */
    public static void tick(GraphicsContext gc) {
        // конец игры.
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);
            return;
        }
        // победа.
        if (winGame) {
            gc.setFill(Color.GREEN);
            gc.setFont(new Font("", 50));
            gc.fillText("GOOD GAME", 100, 250);
            return;
        }

        // движение тела змейки.
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        // смена направления, движение головы и проверка на выход за стены.
        if (direction == Dir.up) {
            snake.get(0).y--;
            if (snake.get(0).y < 0) {
                gameOver = true;
            }
        } else if (direction == Dir.down) {
            snake.get(0).y++;
            if (snake.get(0).y > height) {
                gameOver = true;
            }
        } else if (direction == Dir.left) {
            snake.get(0).x--;
            if (snake.get(0).x < 0) {
                gameOver = true;
            }
        } else if (direction == Dir.right) {
            snake.get(0).x++;
            if (snake.get(0).x > width) {
                gameOver = true;
            }
        }

        // скушал еду.
        if (foodX == snake.get(0).x && foodY == snake.get(0).y) {
            snake.add(new Cell(-1, -1));
            newFood();
        }
        // смерть об себя.
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                break;
            }
        }
        // условие победы.
        if (speed - 6 == 15) {
            winGame = true;
        }
        // раскрашиваем задний фон.
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * cellSize, height * cellSize);

        // счёт.
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + (speed - 6), 10, 30);

        // еда.
        gc.setFill(Color.WHITE);
        gc.fillOval(foodX * cellSize, foodY * cellSize, cellSize, cellSize);

        // голова змейки.
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(snake.get(0).x * cellSize, snake.get(0).y * cellSize,
                cellSize - 1, cellSize - 1);

        // тело змейки.
        for (int k = 1; k < snake.size() - 1; k++) {
            Cell c = snake.get(k);
            gc.setFill(Color.GREEN);
            gc.fillRect(c.x * cellSize, c.y * cellSize, cellSize - 2, cellSize - 2);
        }
        // хвост змейки.
        if (snake.size() > 1) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillOval(snake.get(snake.size() - 1).x * cellSize,
                    snake.get(snake.size() - 1).y * cellSize, cellSize - 1, cellSize - 1);
        }
    }

    // новая еда.
    public static void newFood() {
        while (true) {
            //рандомим координаты.
            foodX = rand.nextInt(width);
            foodY = rand.nextInt(height);
            // проверяем не выпали ли координаты еды на координату змейки.
            for (Cell c : snake) {
                if (c.x == foodX && c.y == foodY) {
                    return;
                }
            }
            // увеличиваем скорость.
            speed++;
            break;
        }
    }

    /**
     * main.
     */
    public static void main(String[] args) {
        launch(args);
    }
}