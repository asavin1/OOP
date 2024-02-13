package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Граф.
 */
public abstract class Graph<T> {
    protected ArrayList<Edge<T>> listofEdges;
    protected ArrayList<Vertex<T>> listofVertexs;

    /**
     * Конструктор.
     * start - начальная вершина нового ребра.
     * end - конечная вершина нового ребра.
     * value - вес новой вершины.
     */
    public Graph(Vertex<T> start, Vertex<T> end, int value) {
        this.listofEdges = new ArrayList<>();
        this.listofVertexs = new ArrayList<>();
        this.listofEdges.add(new Edge<T>(start, end, value));
        this.listofVertexs.add(start);
        this.listofVertexs.add(end);
    }

    /**
     * Конструктор.
     * edge - новое ребро.
     */
    public Graph(Edge<T> edge) {
        this.listofEdges = new ArrayList<>();
        this.listofVertexs = new ArrayList<>();
        this.listofEdges.add(edge);
        this.listofVertexs.add(edge.getStartvertex());
        this.listofVertexs.add(edge.getEndvertex());
    }

    /**
     * Конструктор.
     * listEdges   - список рёбер.
     * listVertexs - список вершин.
     */
    public Graph(ArrayList<Edge<T>> listEdges, ArrayList<Vertex<T>> listVertexs) {
        this.listofEdges = new ArrayList<>();
        this.listofVertexs = new ArrayList<>();
        this.listofEdges.addAll(listEdges);
        this.listofVertexs.addAll(listVertexs);
        for (var edge : listEdges) {
            if (!this.listofVertexs.contains(edge.getEndvertex())) {
                this.listofVertexs.add(edge.getEndvertex());
            }
            if (!this.listofVertexs.contains(edge.getStartvertex())) {
                this.listofVertexs.add(edge.getStartvertex());
            }
        }
    }

    /**
     * Getter для списка рёбер.
     */
    public ArrayList<Edge<T>> getListofEdges() {
        return this.listofEdges;
    }

    /**
     * Getter для списка вершин.
     */
    public ArrayList<Vertex<T>> getListofVertexs() {
        return this.listofVertexs;
    }

    /**
     * Считываем граф с файла с именем filname.
     * В первой строке количество рёбер.
     * далее n строк вида:
     * Стартовая вершина, конечная вершина и вес ребра.
     */
    public Graph<T> readFromfile(String filename) {
        try (var scanner = new Scanner(new File(filename))) {
            int numberEdges = scanner.nextInt();

            var listofVertexs = new ArrayList<Vertex<T>>();
            var listofEdges = new ArrayList<Edge<T>>();

            for (int i = 0; i < numberEdges; i++) {
                var startVertex = (Vertex<T>) new Vertex<>(scanner.next());
                var endVertex = (Vertex<T>) new Vertex<>(scanner.next());
                var weight = scanner.nextInt();

                listofEdges.add(new Edge<>(startVertex.addtoList(listofVertexs),
                        endVertex.addtoList(listofVertexs), weight));
            }
            this.listofEdges = listofEdges;
            this.listofVertexs = listofVertexs;
            return this;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Добавление ребра в граф.
     */
    public abstract void addEdge(Edge<T> edge);

    /**
     * Удаление ребра из графа.
     */
    public abstract void removeEdge(Edge<T> edge);

    /**
     * Добавление вершины в граф.
     */
    public abstract void addVertex(Vertex<T> vertex);

    /**
     * Удаление вершины из графа.
     */
    public abstract void removeVertex(Vertex<T> vertex);

    /**
     * изменение веса ребра.
     */
    public abstract void changeWeight(Edge<T> edge, int newValue);

    /**
     * Кратчайший путь.
     */
    public abstract ArrayList<Vertex<T>> shortestPath(Vertex<T> vertex);
}