package org.example.pizzeria;

import java.util.Queue;

/**
 * Класс для считывания состояния заказов из json.
 */
public class OrdersState {
    private Queue<Integer> orders; //очередь заказов.
    private Queue<Integer> storage; //состояние склада.

    /**
     * Getter для очереди заказов.
     */
    public Queue<Integer> getOrders() {
        return orders;
    }

    /**
     * Setter для очереди заказов.
     */
    public void setOrders(Queue<Integer> orders) {
        this.orders = orders;
    }

    /**
     * Getter для получения очереди, отвечающей за склад.
     */
    public Queue<Integer> getStorage() {
        return storage;
    }

    /**
     * Setter для получения очереди, отвечающей за склад.
     */
    public void setStorage(Queue<Integer> storage) {
        this.storage = storage;
    }
}
