package org.example.pizzeria;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * Пекарь.
 */
public class Baker extends Thread {
    private static final Logger logger = LogManager.getLogger(Baker.class);
    private final ImyQueue<Integer> orders; //заказы
    private final ImyQueue<Integer> storage; //склад
    private final int timeToCook; //время готовки
    private final ArrayList<Long> progressCooking;  //процесс выполнения заказа.

    /**
     * Конструктор.
     */
    public Baker(int timeToCook, ImyQueue<Integer> orders,
                 ImyQueue<Integer> storage, ArrayList<Long> progressCooking) {
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
            long startTime = System.currentTimeMillis();
            long alreadyCooked = progressCooking.get(order);  //время, сколько уже готовили
            try {
                /*
                 * проблема может возникнуть если один повар уже 400мс готовил, а другой когда взял,
                 * у него время готовки меньше 400 в таком случае он мгновенно заканчивает заказ.
                 */
                if (timeToCook - alreadyCooked > 0)
                    Thread.sleep(timeToCook - alreadyCooked); //готовим
            } catch (InterruptedException e) {
                try {
                    // записываем сколько уже готовили до этого + на этот раз
                    progressCooking.set(order, alreadyCooked + System.currentTimeMillis() - startTime);
                    orders.push(order);
                } catch (InterruptedException e2) {
                    throw new RuntimeException(e2);
                }
                logger.info("Order " + order + " return to queue");
                return;
            }
            try {
                storage.push(order); //закидываем на склад готовый заказ
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            logger.info("Order " + order + " done and at storage now");
            progressCooking.set(order, 0L);
        }
    }
}
