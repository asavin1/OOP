package org.example.pizzeria;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Пиццерия.
 */
public class Pizzeria extends Thread {
    //рабочее время.
    private final int workingHours;
    //очередь заказов.
    private final MyQueue<Integer> orders;
    //состояние склада.
    private final MyQueue<Integer> storage;
    //список пекарей.
    private final List<Thread> bakers;
    //список курьеров.
    private final List<Thread> couriers;
    //имя json файла с заказами и состоянием склада.
    private static final String jsonOrdersState = "ordersState.json";
    //имя json файла с конфигурацией пиццерии.
    private static final String jsonConfig = "pizzeriaConfig.json";

    /**
     * Конструктор.
     */
    public Pizzeria(int workingHours) throws IOException {
        //нужен для преобразования json файла в объект PizzaConfiguration.
        ObjectMapper objectMapper = new ObjectMapper();
        //создаём объект PizzaConfiguration и записываем туда конфигурацию из json.
        PizzaConfiguration config;
        try (InputStream fileInputStream = ClassLoader.getSystemResourceAsStream(jsonConfig)) {
            config = objectMapper.readValue(fileInputStream, PizzaConfiguration.class);
        }
        //нужен для преобразования json файла в объект OrdersState.
        ObjectMapper objectMapper2 = new ObjectMapper();
        //создаём объект OrdersState и записываем туда конфигурацию из json.
        OrdersState ordersState;
        orders = new MyQueue<>();
        storage = new MyQueue<>(config.getStorageCapacity());
        try (FileInputStream fileInputStream = new FileInputStream(jsonOrdersState)) {
            ordersState = objectMapper2.readValue(fileInputStream, OrdersState.class);
        }
        orders.setQueue(ordersState.getOrders());
        storage.setQueue(ordersState.getStorage());
        //записываем необходимые поля
        this.workingHours = workingHours;
        this.bakers = new ArrayList<>();
        this.couriers = new ArrayList<>();
        for (var cookingTime : config.getBakersTimeToCook()) {
            this.bakers.add(new Baker(cookingTime, orders, storage));
        }
        for (var capacity : config.getCouriersCapacity()) {
            this.couriers.add(new Courier(capacity, storage));
        }
    }

    /**
     * Добавление заказа.
     */
    public void addOrder(int numberOfOrder) throws InterruptedException {
        orders.push(numberOfOrder);
    }

    /**
     * Начало работы.
     */
    private void initWorking() {
        for (var bakerThread : bakers) {
            bakerThread.start();
        }
        for (var deliverymanThread : couriers) {
            deliverymanThread.start();
        }
    }

    /**
     * Прекращение работы.
     */
    private void haltWork() throws InterruptedException {
        for (var bakerThread : bakers) {
            bakerThread.interrupt();
        }
        for (var deliverymanThread : couriers) {
            deliverymanThread.interrupt();
        }
        //ждём пока остановится
        for (var bakerThread : bakers) {
            bakerThread.join();
        }
        for (var deliverymanThread : couriers) {
            deliverymanThread.join();
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
        System.out.println("Working is over");
        //сохраняем заказы в json
        try {
            saveOrders();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
