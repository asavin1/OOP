package com.example.snake;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;

import static com.example.snake.SnakeGame.*;
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
        SnakeGame game = new SnakeGame();
        newFood();
        assertTrue(game.foodX < game.width && game.foodX > 0
                && game.foodY > 0 && game.foodY < game.height);
    }

    /**
     * Тестируем один кадр.
     */
    @Test
    public void testStart() {
        SnakeGame game = new SnakeGame();
        snake.add(new Cell(10, 10));
        game.direction = Dir.right;
        Canvas c = new Canvas(game.width * game.cellSize, game.height * game.cellSize);
        GraphicsContext gc = c.getGraphicsContext2D();
        tick(gc);
        assertTrue(snake.get(0).x == 11 && snake.get(0).y == 10);
    }
}
