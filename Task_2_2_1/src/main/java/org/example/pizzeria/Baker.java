package org.example.pizzeria;

/**
 * пекарь.
 */
public class Baker extends Thread {
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
            System.out.println(order + " is cooking\n");

            try {
                Thread.sleep(timeToCook); //готовим
                storage.push(order); //закидываем на склад готовый заказ
            } catch (InterruptedException e) {
                try {
                    orders.push(order);
                } catch (InterruptedException e2) {
                    throw new RuntimeException(e2);
                }
                System.out.printf("%d order return to queue\n", order);
                return;
            }
            System.out.println(order + " done and at storage now\n");
        }
    }
}
