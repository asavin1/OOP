package com.example.snake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс для тестирования.
 */
public class SnakeGameTest {

    /**
     * Тестируем спавн еды.
     */
    @Test
    public void testNewFood() {
        for (int i = 0; i < SnakeModel.width; i++) {
            for (int j = 0; j < SnakeModel.height; j++) {
                var cell = new SnakeModel.Cell(i, j);
                SnakeModel.freeCells.add(cell);
            }
        }
        SnakeModel.newFood();
        assertTrue(SnakeModel.food.get(0).x < SnakeModel.width && SnakeModel.food.get(0).x >= 0
                && SnakeModel.food.get(0).y >= 0 && SnakeModel.food.get(0).y < SnakeModel.height);
    }

    /**
     * Тестируем рестарт.
     */
    @Test
    public void testRestartGame() {
        SnakeModel.restartGame();
        assertTrue(SnakeModel.direction == SnakeModel.Dir.left
                && SnakeModel.speed == 5);
    }

    /**
     * Тестируем движение.
     */
    @Test
    public void testMove() {
        SnakeModel.restartGame();
        SnakeModel.snake.add(new SnakeModel.Cell(SnakeModel.width / 2, SnakeModel.height / 2));
        SnakeModel.move();
        assertTrue(SnakeModel.snake.get(0).x == SnakeModel.width / 2 - 1
                && SnakeModel.snake.get(0).y == SnakeModel.height / 2);
    }

    /**
     * Тестируем столкновение о стенку.
     */
    @Test
    public void testWallCollision() {
        SnakeModel.restartGame();
        SnakeModel.snake.add(new SnakeModel.Cell(10, 10));
        SnakeModel.walls.add(new SnakeModel.Cell(10, 10));
        SnakeModel.checkCollisions();
        assertTrue(SnakeModel.gameOver);
    }

    /**
     * Тестируем тик игры.
     */
    @Test
    public void testTick() {
        SnakeModel.restartGame();
        VBox root = new VBox();
        Canvas c = new Canvas(SnakeModel.width * SnakeModel.cellSize,
                SnakeModel.height * SnakeModel.cellSize);
        GraphicsContext gc = c.getGraphicsContext2D();
        root.getChildren().add(c);
        SnakeModel.tick(gc);
        assertFalse(SnakeModel.gameOver);
    }
}
