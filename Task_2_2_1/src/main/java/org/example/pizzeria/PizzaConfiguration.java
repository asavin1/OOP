package org.example.pizzeria;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Конфигурация.
 */
public class PizzaConfiguration {
    private List<Integer> bakersTimeToCook; //время готовки пекарей
    private List<Integer> couriersCapacity;  //вместимость сумок курьеров.
    private int storageCapacity;  //вместимость склада.
    private int workingHours; //рабочее время.
    //имя json файла с конфигурацией пиццерии.
    private static final String jsonConfig = "pizzeriaConfig.json";

    /**
     * Getter для времени готовки пекарей.
     */
    public List<Integer> getBakersTimeToCook() {
        return bakersTimeToCook;
    }

    /**
     * Setter для времени готовки пекарей.
     */
    public void setBakersTimeToCook(List<Integer> bakersTimeToCook) {
        this.bakersTimeToCook = bakersTimeToCook;
    }

    /**
     * Getter для вместимости сумок курьеров.
     */
    public List<Integer> getCouriersCapacity() {
        return couriersCapacity;
    }

    /**
     * Setter для вместимости сумок курьеров.
     */
    public void setCouriersCapacity(List<Integer> couriersCapacity) {
        this.couriersCapacity = couriersCapacity;
    }

    /**
     * Getter вместимости склада.
     */
    public int getStorageCapacity() {
        return storageCapacity;
    }

    /**
     * Setter вместимости склада.
     */
    public void setStorageCapacity(int storageCapacity) {
        this.storageCapacity = storageCapacity;
    }

    /**
     * Getter для времени работы пекарни.
     */
    public int getWorkingHours() {
        return workingHours;
    }

    /**
     * Getter для времени готовки пекарей.
     */
    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    /**
     * Установка конфигурации.
     */
    public static PizzaConfiguration setConfig() throws IOException {
        //нужен для преобразования json файла в объект PizzaConfiguration.
        ObjectMapper objectMapper = new ObjectMapper();
        PizzaConfiguration config;
        try (InputStream fileInputStream = ClassLoader.getSystemResourceAsStream(jsonConfig)) {
            config = objectMapper.readValue(fileInputStream, PizzaConfiguration.class);
        }
        return config;
    }
}
