package com.example.snake;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Да.
 */
public class HelloController {
    @FXML
    private Label welcomeText;

    /**
     * Да.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}