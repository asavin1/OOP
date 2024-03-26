package org.example.pizzeria;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * пекарь.
 */
public class Baker extends Thread {
    private static final Logger logger = LogManager.getLogger(Baker.class);
    private final MyQueue<Integer> orders; //заказы
    private final MyQueue<Integer> storage; //склад
    private final int timeToCook; //время готовки

    /**
     * Конструктор.
     */
    public Baker(int timeToCook, MyQueue<Integer> orders, MyQueue<Integer> storage) {
        this.timeToCook = timeToCook;
        this.orders = orders;
        this.storage = storage;
    }

    /**
     * run.
     */
    @Override
    public void run() {
        //пока не прервём, будет работать, у меня повара идеальные работают весь день без перерывов
        while (!Thread.currentThread().isInterrupted()) {
            int order;
            try {
                order = orders.pop(); //берём заказ
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            logger.info(order + " is cooking\n");

            try {
                Thread.sleep(timeToCook); //готовим
                storage.push(order); //закидываем на склад готовый заказ
            } catch (InterruptedException e) {
                try {
                    orders.push(order);
                } catch (InterruptedException e2) {
                    throw new RuntimeException(e2);
                }
                logger.info(order + "order return to queue\n");
                return;
            }
            logger.info(order + " done and at storage now\n");
        }
    }
}
