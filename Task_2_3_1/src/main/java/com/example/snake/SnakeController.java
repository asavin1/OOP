package com.example.snake;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;

/**
 * Controller.
 */
public class SnakeController {
    /**
     * Управление клавишами.
     */
    public static void controlKeys(Scene scene) {
        // управляем клавишами и устанавливаем направление.
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.R) { // Клавиша R для рестарта
                SnakeModel.restartGame();
            }
            if (key.getCode() == KeyCode.W && SnakeModel.direction != SnakeModel.Dir.down) {
                SnakeModel.direction = SnakeModel.Dir.up;
            }
            if (key.getCode() == KeyCode.A && SnakeModel.direction != SnakeModel.Dir.right) {
                SnakeModel.direction = SnakeModel.Dir.left;
            }
            if (key.getCode() == KeyCode.S && SnakeModel.direction != SnakeModel.Dir.up) {
                SnakeModel.direction = SnakeModel.Dir.down;
            }
            if (key.getCode() == KeyCode.D && SnakeModel.direction != SnakeModel.Dir.left) {
                SnakeModel.direction = SnakeModel.Dir.right;
            }
        });
    }
}
