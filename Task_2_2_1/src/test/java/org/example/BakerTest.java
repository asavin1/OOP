package org.example;

import java.util.ArrayList;
import java.util.Collections;
import org.example.pizzeria.Baker;
import org.example.pizzeria.MyQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тестируем пекаря.
 */
public class BakerTest {
    /**
     * Делаем 10 заказов и проверяем ушли ли они из очереди и попали ли на склад.
     */
    @Test
    public void testBaker() throws InterruptedException {
        MyQueue<Integer> orders = new MyQueue<>();
        MyQueue<Integer> storage = new MyQueue<>();
        ArrayList<Long> list = new ArrayList<>(Collections.nCopies(11, 0L));
        int timeToCook = 100;
        Baker baker = new Baker(timeToCook, orders, storage, list);
        baker.start();
        for (int i = 1; i <= 10; i++) {
            orders.push(i);
        }
        Thread.sleep(2000);
        baker.interrupt();
        baker.join();
        for (int i = 1; i <= 10; i++) {
            Assertions.assertFalse(orders.getQueue().contains(i));
            Assertions.assertTrue(storage.getQueue().contains(i));
        }
    }

    /**
     * Делаем 10 заказов.
     * 6 должны сделаться и попасть на склад.
     * 7 должен начать делаться, но вернуться в очередь.
     */
    @Test
    public void testInterrupting() throws InterruptedException {
        MyQueue<Integer> orders = new MyQueue<>();
        MyQueue<Integer> storage = new MyQueue<>();
        ArrayList<Long> list = new ArrayList<>(Collections.nCopies(11, 0L));
        int timeToCook = 150;
        Baker baker = new Baker(timeToCook, orders, storage, list);
        for (int i = 1; i <= 10; i++) {
            orders.push(i);
        }
        baker.start();
        Thread.sleep(1000);
        baker.interrupt();
        baker.join();
        for (int i = 1; i <= 6; i++) {
            Assertions.assertFalse(orders.getQueue().contains(i));
            Assertions.assertTrue(storage.getQueue().contains(i));
        }
        for (int i = 7; i <= 10; i++) {
            Assertions.assertTrue(orders.getQueue().contains(i));
            Assertions.assertFalse(storage.getQueue().contains(i));
        }
    }
}