package com.example.snake;

/**
 * Класс для запуска.
 */
public class SnakeApp {
    /**
     * Точка входа.
     */
    public static void main(String[] args) {
        //сбрасываем настройки.
        SnakeModel.restartGame();

        //делаем окошко и запускаем.
        SnakeView snakeView = new SnakeView();
        snakeView.launchApp(args);

        //выставляем управление клавиатурой.
        SnakeController.controlKeys(snakeView.scene);
    }
}
