package org.example.pizzeria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * пекарь.
 */
public class Baker extends Thread {
    private static final Logger logger = LogManager.getLogger(Baker.class);
    private final ImyQueue<Integer> orders; //заказы
    private final ImyQueue<Integer> storage; //склад
    private final int timeToCook; //время готовки
    private final ArrayList<Integer> progressCooking;  //процесс выполнения заказа.

    /**
     * Конструктор.
     */
    public Baker(int timeToCook, ImyQueue<Integer> orders,
                 ImyQueue<Integer> storage, ArrayList<Integer> progressCooking) {
        this.timeToCook = timeToCook;
        this.orders = orders;
        this.storage = storage;
        this.progressCooking = progressCooking;
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
            logger.info("Order " + order + " is cooking");
            int progress = progressCooking.get(order); // переменная для отслеживания прогресса
            while (progress < 100) {
                try {
                    Thread.sleep(timeToCook / 10); //готовим
                    progress += 10;// увеличиваем прогресс на 10%
                    logger.info("Order " + order + " Cooking Progress: " + progress + "%");
                } catch (InterruptedException e) {
                    try {
                        progressCooking.set(order, progress);
                        orders.push(order);
                    } catch (InterruptedException e2) {
                        throw new RuntimeException(e2);
                    }
                    logger.info("Order " + order + " return to queue");
                    return;
                }
            }
            try {
                storage.push(order); //закидываем на склад готовый заказ
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info("Order " + order + " done and at storage now");
            progressCooking.set(order, 100);
        }
    }
}
