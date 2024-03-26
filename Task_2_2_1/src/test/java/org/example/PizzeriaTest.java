package org.example;

import org.example.pizzeria.Pizzeria;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Тестируем пиццерию.
 */
public class PizzeriaTest {
    /**
     * Просто тестируем работу.
     */
    @Test
    public void testPizza() throws InterruptedException, IOException {
        Pizzeria pizzeria;

        pizzeria = new Pizzeria();
        pizzeria.start();
        for (int i = 1; i <= 10; i++) {
            pizzeria.addOrder(i);
        }
        pizzeria.join();
        assertFalse(pizzeria.isAlive());
    }
}
