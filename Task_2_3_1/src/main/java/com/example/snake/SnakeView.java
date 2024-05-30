package com.example.snake;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * View.
 */
public class SnakeView extends Application {

    protected Scene scene;

    /**
     * Запуск приложения.
     */
    public void launchApp() {
        launch();
    }

    /**
     * Такая тема.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // создаём нужные вещи.
            VBox root = new VBox();
            Canvas c = new Canvas(SnakeModel.width * SnakeModel.cellSize,
                    SnakeModel.height * SnakeModel.cellSize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);
            /*
              Класс необходимый для анимации.
             */
            Thread gameThread = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        Thread.sleep(1000 / SnakeModel.speed);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    SnakeModel.tick(gc);
                }
            });
            gameThread.start();

            primaryStage.setOnCloseRequest(event -> {
                gameThread.interrupt();
                try {
                    gameThread.join(); // Ждём завершения потока
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Application is closing...");
            });

            scene = new Scene(root, SnakeModel.width * SnakeModel.cellSize,
                    SnakeModel.height * SnakeModel.cellSize);

            SnakeController.controlKeys(scene);

            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Отрисовка всех приколов.
     */
    public static void render(GraphicsContext gc) {
        // раскрашиваем задний фон.
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, SnakeModel.width * SnakeModel.cellSize,
                SnakeModel.height * SnakeModel.cellSize);

        //смерть о стенку
        for (SnakeModel.Cell wall : SnakeModel.walls) {
            gc.setFill(Color.ORANGE);
            gc.fillRect(wall.x * SnakeModel.cellSize, wall.y * SnakeModel.cellSize,
                    SnakeModel.cellSize, SnakeModel.cellSize);
        }

        // еда.
        for (var cell : SnakeModel.food) {
            gc.setFill(Color.WHITE);
            gc.fillOval(cell.x * SnakeModel.cellSize, cell.y * SnakeModel.cellSize,
                    SnakeModel.cellSize, SnakeModel.cellSize);
        }

        // голова змейки.
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(SnakeModel.snake.get(0).x * SnakeModel.cellSize,
                SnakeModel.snake.get(0).y * SnakeModel.cellSize,
                SnakeModel.cellSize - 1, SnakeModel.cellSize - 1);

        // тело змейки.
        for (int k = 1; k < SnakeModel.snake.size(); k++) {
            SnakeModel.Cell c = SnakeModel.snake.get(k);
            gc.setFill(Color.GREEN);
            gc.fillRect(c.x * SnakeModel.cellSize, c.y * SnakeModel.cellSize,
                    SnakeModel.cellSize - 2, SnakeModel.cellSize - 2);
        }

        // счёт.
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + (SnakeModel.speed - 5), 10, 30);
    }
}

