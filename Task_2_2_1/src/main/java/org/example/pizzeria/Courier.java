package org.example.pizzeria;

import java.util.ArrayList;
import java.util.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Курьер.
 */
public class Courier extends Thread {
    private static final Logger logger = LogManager.getLogger(Courier.class);
    private static final int timeToDelivery = 1000; //ну да, всё за секунду доставляют
    private final ImyQueue<Integer> storage;  //склад.
    private final int capacity;  //вместимость сумки курьера.
    private final ArrayList<Integer> progressDelivering;  //процесс выполнения заказа.

    /**
     * Конструктор.
     */
    public Courier(int capacity, ImyQueue<Integer> storage,
                   ArrayList<Integer> progressDelivering) {
        this.capacity = capacity;
        this.storage = storage;
        this.progressDelivering = progressDelivering;
    }

    /**
     * run.
     */
    @Override
    public void run() {
        //пока не прервём будет работать, у меня курьеры идеальные работают весь день без перерывов
        while (!Thread.currentThread().isInterrupted()) {
            int currCapacity = 0; //оставшееся место
            int order;
            ArrayList<Integer> bag = new ArrayList<>();

            //если место в сумке ещё есть и на складе есть готовые заказы
            while (currCapacity < capacity && storage.size() != 0) {
                try {
                    order = storage.pop();
                } catch (InterruptedException e) {
                    return;
                }
                bag.add(order);
                logger.info("Order " + order + " is delivering");
                currCapacity++;
            }
            Integer minProgress = Collections.min(progressDelivering);
            while (minProgress < 100) { // пока прогресс доставки не достигнет 100%
                try {
                    Thread.sleep(timeToDelivery / 10);
                    for (Integer curOrder : bag) {
                        int newValue = progressDelivering.get(curOrder) + 10;
                        progressDelivering.set(curOrder, newValue);
                        logger.info("Order " + curOrder + " Delivery Progress: "
                                + progressDelivering.get(curOrder) + "%");
                    }
                    minProgress += 10;
                } catch (InterruptedException e) {
                    try {
                        for (Integer curOrder : bag) {
                            if (progressDelivering.get(curOrder) < 100) {
                                storage.push(curOrder);
                            }
                        }
                    } catch (InterruptedException e2) {
                        throw new RuntimeException(e2);
                    }
                    for (Integer curOrder : bag) {
                        logger.info("Order " + curOrder + " return to storage");
                    }
                    return;
                }
            }
            for (Integer curOrder : bag) {
                if (progressDelivering.get(curOrder) >= 100) {
                    logger.info("Order " + curOrder + " delivered");
                }
            }
        }
    }
}
