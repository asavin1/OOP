package org.example.pizzeria;

import java.io.IOException;

/**
 * main.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Pizzeria pizzeria;

        try {
            pizzeria = new Pizzeria(10000);
        } catch (IOException e) {
            System.out.println("Не удаётся найти файл");
            return;
        }
        pizzeria.start();
        for (int i = 1; i <= 10; i++) {
            pizzeria.addOrder(i);
        }
        pizzeria.join();
    }
}