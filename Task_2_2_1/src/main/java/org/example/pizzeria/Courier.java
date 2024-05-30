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
    private final ArrayList<Long> progressDelivering;  //процесс выполнения заказа.

    /**
     * Конструктор.
     */
    public Courier(int capacity, ImyQueue<Integer> storage,
                   ArrayList<Long> progressDelivering) {
        this.capacity = capacity;
        this.storage = storage;
        this.progressDelivering = progressDelivering;
    }

    /**
     * run.
     */
    @Override
    public void run() {
        //пока не прервём - будет работать, у меня курьеры идеальные работают весь день без перерывов
        while (!Thread.currentThread().isInterrupted()) {
            int currCapacity = 0; //оставшееся место
            int order;
            ArrayList<Integer> bag = new ArrayList<>();
            long minTime = timeToDelivery; //мин время, сколько уже доставлялся заказ из тех, которые в сумке

            //если место в сумке ещё есть и на складе есть готовые заказы
            while (currCapacity < capacity && storage.size() != 0) {
                try {
                    order = storage.pop();
                } catch (InterruptedException e) {
                    return;
                }
                bag.add(order);
                if (progressDelivering.get(order) < minTime) {
                    minTime = progressDelivering.get(order);
                }
                logger.info("Order " + order + " is delivering");
                currCapacity++;
            }
            long startTime = System.currentTimeMillis();
            try {
                Thread.sleep(timeToDelivery - minTime);
            } catch (InterruptedException e) {
                try {
                    for (Integer curOrder : bag) {
                        long elapsed = System.currentTimeMillis() - startTime;  //прошедшее время
                        long alreadyDelivered = progressDelivering.get(curOrder); //время, сколько уже доставляли
                        if (elapsed < timeToDelivery) {
                            progressDelivering.set(curOrder, elapsed + alreadyDelivered);
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
            for (Integer curOrder : bag) {
                logger.info("Order " + curOrder + " delivered");
                progressDelivering.set(curOrder, 0L);
            }
        }
    }
}
