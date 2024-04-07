package org.example.pizzeria;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Класс для считывания состояния заказов из json.
 */
public class OrdersState {
    private Queue<Integer> orders; //очередь заказов.
    private Queue<Integer> storage; //состояние склада.
    private ArrayList<Integer> progressCooking;  //процесс выполнения заказа.
    private ArrayList<Integer> progressDelivering;  //процесс доставки заказа.

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

    /**
     * Getter для прогресса готовки.
     */
    public ArrayList<Integer> getProgressCooking() {
        return progressCooking;
    }

    /**
     * Setter для прогресса готовки.
     */
    public void setProgressCooking(ArrayList<Integer> progressCooking) {
        this.progressCooking = progressCooking;
    }

    /**
     * Getter для прогресса доставки.
     */
    public ArrayList<Integer> getProgressDelivering() {
        return progressDelivering;
    }

    /**
     * Setter для прогресса доставки.
     */
    public void setProgressDelivering(ArrayList<Integer> progressDelivering) {
        this.progressDelivering = progressDelivering;
    }
}