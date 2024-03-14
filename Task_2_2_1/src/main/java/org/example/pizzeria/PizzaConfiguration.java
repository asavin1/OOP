package org.example.pizzeria;

import java.util.List;

/**
 * Конфигурация.
 */
public class PizzaConfiguration {
    private List<Integer> bakersTimeToCook; //время готовки пекарей
    private List<Integer> couriersCapacity;  //вместимость сумок курьеров.
    private int storageCapacity;  //вместимость склада.

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
}
