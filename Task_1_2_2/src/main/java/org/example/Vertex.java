package org.example;

import java.util.ArrayList;

/**
 * Вершина.
 */
public class Vertex<T> {
    private T value;

    private int dist;

    /**
     * конструктор вершины.
     */
    public Vertex(T value) {
        this.value = value;
        this.dist = -1;
    }

    /**
     * Getter для значения вершниы.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Getter для дистанции.
     */
    public int getDist() {
        return this.dist;
    }

    /**
     * Изменение вершины.
     */
    public void changeValue(T value) {
        this.value = value;
    }

    /**
     * Изменение дистанции.
     */
    public void changeDist(int newDist) {
        this.dist = newDist;
    }

    /**
     * Добавление this в список list если её там нет.
     */
    public Vertex<T> addtoList(ArrayList<Vertex<T>> list) {
        //проверяем есть ли она уже в списке.
        for (var v : list) {
            if (v.equals(this)) {
                return v;
            }
        }
        list.add(this);
        return this;
    }

    /**
     * equals.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var vertex = (Vertex<?>) obj;
        //сравниваем по значению.
        return this.value.equals(vertex.value);
    }
}