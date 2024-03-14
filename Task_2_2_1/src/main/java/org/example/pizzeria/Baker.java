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
            System.out.println(order + " готовится\n");

            try {
                Thread.sleep(timeToCook); //готовим
                storage.push(order); //закидываем на склад готовый заказ
            } catch (InterruptedException e) {
                try {
                    orders.push(order);
                } catch (InterruptedException e2) {
                    throw new RuntimeException(e2);
                }
                System.out.printf("%d заказ вернулся в очередь\n", order);
                return;
            }
            System.out.println(order + " готов и уже на складе\n");
        }
    }
}
