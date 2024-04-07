package org.example;

import org.example.pizzeria.MyQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тестируем очередь.
 */
public class MyQueueTest {
    /**
     * Тестируем pop и push.
     */
    @Test
    public void testPushAndPop() throws InterruptedException {
        MyQueue<Integer> myQueue = new MyQueue<>(1);
        myQueue.push(1);
        Assertions.assertEquals(1, myQueue.pop());
    }

    /**
     * Тестируем getCapacity.
     */
    @Test
    public void testGetCapacity() {
        MyQueue<Integer> myQueue = new MyQueue<>(1);
        Assertions.assertEquals(1, myQueue.getCapacity());
    }

    /**
     * Тестируем set и get.
     */
    @Test
    public void testSetAndGetQueue() throws InterruptedException {
        MyQueue<String> myQueue = new MyQueue<>();
        myQueue.push("1");
        MyQueue<String> newQueue = new MyQueue<>();
        newQueue.setQueue(myQueue.getQueue());
        Assertions.assertEquals("1", newQueue.pop());
    }
}
