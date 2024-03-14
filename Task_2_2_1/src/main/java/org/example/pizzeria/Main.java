package org.example.pizzeria;

import java.io.IOException;

/**
 * main.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Pizzeria pizzeria;

        try {
            // Create a pizzeria instance with a working time of 20,000 milliseconds
            pizzeria = new Pizzeria(10000);
        } catch (IOException e) {
            System.out.println("Не удаётся найти файл");
            return;
        }

        // Start the pizzeria simulation
        pizzeria.start();

        // Add orders to the pizzeria
        for (int i = 1; i <= 10; i++) {
            pizzeria.addOrder(i);
        }

        // Wait for the pizzeria simulation to complete
        pizzeria.join();
    }
}