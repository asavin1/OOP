package org.example;

import java.io.IOException;
import org.example.pizzeria.Pizzeria;
import org.junit.jupiter.api.Test;

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
        pizzeria.join();
        assertFalse(pizzeria.isAlive());
    }
}
