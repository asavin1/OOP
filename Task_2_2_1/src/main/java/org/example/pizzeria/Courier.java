package org.example.pizzeria;

/**
 * Курьер.
 */
public class Courier extends Thread {
    private static final int timeToDelivery = 1000; //ну да, всё за секунду доставляют
    private final MyQueue<Integer> storage;  //склад.
    private final int capacity;  //вместимость сумки курьера.

    /**
     * Конструктор.
     */
    public Courier(int capacity, MyQueue<Integer> storage) {
        this.capacity = capacity;
        this.storage = storage;
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

            //если место в сумке ещё есть и на складе есть готовые заказы
            while (currCapacity < capacity && storage.size() != 0) {
                try {
                    order = storage.pop();
                } catch (InterruptedException e) {
                    return;
                }

                System.out.printf("Заказ " + order + " доставляется\n");
                currCapacity++;
            }
            try {
                Thread.sleep(timeToDelivery);  //доставляем
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
