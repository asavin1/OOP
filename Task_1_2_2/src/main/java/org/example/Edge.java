package org.example;

import java.util.ArrayList;

/**
 * Ребро.
 */
public class Edge<T> {

    private final Vertex<T> startVertex;
    private final Vertex<T> endVertex;
    private int weight;

    /**
     * конструктор ребра.
     * startVertex - начальная вершина.
     * endVertex - конечная вершина.
     * weight - вес.
     */
    public Edge(Vertex<T> start, Vertex<T> end, int weight) {
        this.startVertex = start;
        this.endVertex = end;
        this.weight = weight;
    }

    /**
     * Getter для веса.
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Getter для начальной вершины.
     */
    public Vertex<T> getStartvertex() {
        return this.startVertex;
    }

    /**
     * Getter для конечной вершины.
     */
    public Vertex<T> getEndvertex() {
        return this.endVertex;
    }

    /**
     * Добавление this в список list если её там нет.
     */
    public Edge<T> addtoList(ArrayList<Edge<T>> list) {
        for (var e : list) {
            if (e.equals(this)) {
                return e;
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
        var edge = (Edge<?>) obj;
        if (edge.getEndvertex().equals(this.endVertex)) {
            if (edge.getStartvertex().equals(this.startVertex)) {
                return edge.getWeight() == this.weight;
            }
        }
        return false;
    }
}