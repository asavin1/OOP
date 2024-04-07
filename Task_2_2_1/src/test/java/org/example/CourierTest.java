package org.example;

import java.util.ArrayList;
import java.util.Collections;
import org.example.pizzeria.Baker;
import org.example.pizzeria.Courier;
import org.example.pizzeria.MyQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тестируем курьера.
 */
public class CourierTest {
    /**
     * Делаем 3 заказа и проверяем ушли ли они со склада.
     */
    @Test
    public void testCourier() throws InterruptedException {
        MyQueue<Integer> orders = new MyQueue<>();
        MyQueue<Integer> storage = new MyQueue<>();
        ArrayList<Integer> list = new ArrayList<>(Collections.nCopies(4, 0));
        ArrayList<Integer> list2 = new ArrayList<>(Collections.nCopies(4, 0));
        int timeToCook = 100;
        Baker baker = new Baker(timeToCook, orders, storage, list);
        Courier courier = new Courier(1, storage, list2);
        baker.start();
        courier.start();
        for (int i = 1; i <= 3; i++) {
            orders.push(i);
        }
        Thread.sleep(10000);
        baker.interrupt();
        baker.join();
        courier.interrupt();
        courier.join();
        for (int i = 1; i <= 3; i++) {
            Assertions.assertFalse(orders.getQueue().contains(i));
            Assertions.assertFalse(storage.getQueue().contains(i));
        }
    }
}