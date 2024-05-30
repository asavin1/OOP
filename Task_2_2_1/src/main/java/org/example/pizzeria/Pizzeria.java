package org.example.pizzeria;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Пиццерия.
 */
public class Pizzeria extends Thread {
    private static final Logger logger = LogManager.getLogger(Pizzeria.class);
    //рабочее время.
    private final int workingHours;
    //очередь заказов.
    private final ImyQueue<Integer> orders;
    private final ImyQueue<Integer> storage; //состояние склада.
    //список пекарей.
    private final List<Thread> bakers;
    //список курьеров.
    private final List<Thread> couriers;
    //имя json файла с заказами и состоянием склада.
    private static final String jsonOrdersState = "state.json";
    private ArrayList<Long> progressCooking;  //процесс выполнения заказа.
    private ArrayList<Long> progressDelivering;  //процесс доставки заказа.

    /**
     * Конструктор.
     */
    public Pizzeria() throws IOException {
        PizzaConfiguration config = PizzaConfiguration.setConfig();
        ObjectMapper objectMapper2 = new ObjectMapper();
        OrdersState state;
        orders = new MyQueue<>();
        storage = new MyQueue<>(config.getStorageCapacity());
        progressCooking = new ArrayList<>();
        progressDelivering = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(jsonOrdersState)) {
            state = objectMapper2.readValue(fileInputStream, OrdersState.class);
        }
        workingHours = config.getWorkingHours();
        orders.setQueue(state.getOrders());
        storage.setQueue(state.getStorage());
        progressDelivering = state.getProgressDelivering();
        progressCooking = state.getProgressCooking();
        this.bakers = new ArrayList<>();
        this.couriers = new ArrayList<>();
        for (var cookingTime : config.getBakersTimeToCook()) {
            this.bakers.add(new Baker(cookingTime, orders, storage, progressCooking));
        }
        for (var capacity : config.getCouriersCapacity()) {
            this.couriers.add(new Courier(capacity, storage, progressDelivering));
        }
    }

    /**
     * Начало работы.
     */
    private void initWorking() {
        for (var bakerThread : bakers) {
            bakerThread.start();
        }
        for (var courierThread : couriers) {
            courierThread.start();
        }
    }

    /**
     * Прекращение работы.
     */
    private void haltWork() throws InterruptedException {
        for (var bakerThread : bakers) {
            bakerThread.interrupt();
        }
        for (var courierThread : couriers) {
            courierThread.interrupt();
        }
        //ждём пока остановится
        for (var bakerThread : bakers) {
            bakerThread.join();
        }
        for (var courierThread : couriers) {
            courierThread.join();
        }
    }

    /**
     * Записывает оставшиеся заказы в json.
     */
    private void saveOrders() throws IOException {
        //нужен для преобразования объекта OrdersState в json файл.
        ObjectMapper objectMapper = new ObjectMapper();
        //создаём объект OrderState и записываем туда текущие заказы и состояния склада.
        OrdersState ordersState = new OrdersState();
        ordersState.setOrders(orders.getQueue());
        ordersState.setStorage(storage.getQueue());
        ordersState.setProgressCooking(progressCooking);
        ordersState.setProgressDelivering(progressDelivering);
        //записываем всё в json.
        try (FileOutputStream outputStream = new FileOutputStream(jsonOrdersState)) {
            objectMapper.writeValue(outputStream, ordersState);
        }
    }

    /**
     * run.
     */
    @Override
    public void run() {
        //запускаем пиццерию
        initWorking();
        //процесс спит
        try {
            sleep(workingHours);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //прекращаем работу
        try {
            haltWork();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Working is over\n");
        //сохраняем заказы в json
        try {
            saveOrders();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
