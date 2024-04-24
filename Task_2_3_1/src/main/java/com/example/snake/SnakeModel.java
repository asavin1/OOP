package com.example.snake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Model.
 */
public class SnakeModel {
    protected static int speed = 5;
    protected static final int width = 20;
    protected static final int height = 20;
    protected static final int cellSize = 25;
    protected static final List<Cell> snake = new ArrayList<>();
    protected static Dir direction = Dir.left;
    protected static boolean gameOver = false;
    protected static boolean winGame = false;
    protected static final Random rand = new Random();
    protected static final List<Cell> freeCells = new ArrayList<>();
    protected static final List<Cell> food = new ArrayList<>();
    protected static final List<Cell> walls = new ArrayList<>();

    /**
     * Enum для направления.
     */
    public enum Dir {
        left, right, up, down
    }

    /**
     * Клеточка.
     */
    public static class Cell {
        public int x;
        public int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Tick игры.
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
        move();
        // скушал еду.
        for (int i = 0; i < food.size(); i++) {
            if (food.get(i).x == snake.get(0).x && food.get(i).y == snake.get(0).y) {
                snake.add(new Cell(-1, -1));
                food.remove(food.get(i));
                newFood();
                speed++;
            }
        }
        checkCollisions();
        // условие победы.
        if (speed - 5 == 25) {
            winGame = true;
        }
        SnakeView.render(gc);
    }

    /**
     * Restart.
     */
    public static void restartGame() {
        speed = 5;
        direction = Dir.left;
        gameOver = false;
        winGame = false;
        snake.clear();
        freeCells.clear();
        food.clear();
        walls.clear();
        // добавляем первый кусок змейки.
        snake.add(new Cell(width / 2, height / 2));
        //делаем стенки.
        for (int i = 0; i < 7; i++) {
            walls.add(new Cell(3, i));
            walls.add(new Cell(width - 4, height - i));
        }
        for (int i = 0; i < width - 7; i++) {
            walls.add(new Cell(7 + i, 6));
            walls.add(new Cell(i, height - 6));
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                var cell = new Cell(i, j);
                freeCells.add(cell);
            }
        }
        // Убираем стенки из свободных клеток
        for (Cell wall : walls) {
            freeCells.removeIf(cell -> cell.x == wall.x && cell.y == wall.y);
        }
        // Получаем координаты еды
        newFood();
        newFood();
        newFood();
    }

    /**
     * Генерация новой еды.
     */
    public static void newFood() {
        // Создаем копию списка свободных клеток
        List<Cell> availableCells = new ArrayList<>(freeCells);

        // Удаляем из списка свободных клеток клетки, занятые змейкой
        for (Cell cell : snake) {
            availableCells.removeIf(c -> c.x == cell.x && c.y == cell.y);
        }

        // Если список доступных клеток не пуст, выбираем из него случайную клетку
        if (!availableCells.isEmpty()) {
            var cell = availableCells.get(rand.nextInt(availableCells.size()));
            food.add(cell);
            freeCells.remove(cell);
        }
    }

    /**
     * Проверка столкновений (о себя, о стенки).
     */
    public static void checkCollisions() {
        // смерть об себя.
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
                gameOver = true;
                break;
            }
        }

        //смерть о стенку
        for (Cell wall : walls) {
            if (snake.get(0).x == wall.x && snake.get(0).y == wall.y) {
                gameOver = true;
                break;
            }
        }
    }

    /**
     * Движение змейки.
     */
    public static void move() {
        // движение тела змейки.
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        // смена направления, движение головы
        if (direction == Dir.up) {
            int newY = snake.get(0).y - 1;
            if (newY < 0) {
                snake.get(0).y = height - 1;
            } else {
                snake.get(0).y = newY;
            }
        } else if (direction == Dir.down) {
            int newY = snake.get(0).y + 1;
            if (newY >= height) {
                snake.get(0).y = 0;
            } else {
                snake.get(0).y = newY;
            }
        } else if (direction == Dir.left) {
            int newX = snake.get(0).x - 1;
            if (newX < 0) {
                snake.get(0).x = width - 1;
            } else {
                snake.get(0).x = newX;
            }
        } else if (direction == Dir.right) {
            int newX = snake.get(0).x + 1;
            if (newX >= width) {
                snake.get(0).x = 0;
            } else {
                snake.get(0).x = newX;
            }
        }
    }
}

