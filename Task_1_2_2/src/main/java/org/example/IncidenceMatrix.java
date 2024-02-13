package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * матрица инцидентности.
 */
public class IncidenceMatrix<T> extends Graph<T> {
    private final HashMap<Vertex<T>, HashMap<Edge<T>, Integer>> incidenceMatrix;

    /**
     * Конструктор.
     * start - начальная вершина.
     * end - конечная вершина.
     * weight - вес ребра.
     */
    public IncidenceMatrix(int value, Vertex<T> start, Vertex<T> end) {
        super(start, end, value);
        this.incidenceMatrix = new HashMap<>();
        this.incidenceMatrix.put(start, new HashMap<>());
        this.incidenceMatrix.put(end, new HashMap<>());
        var edge = new Edge<>(start, end, value);
        this.incidenceMatrix.get(start).put(edge, value);
        this.incidenceMatrix.get(end).put(edge, -value);
    }

    /**
     * Конструктор.
     * edge - ребро.
     */
    public IncidenceMatrix(Edge<T> edge) {
        super(edge);
        this.incidenceMatrix = new HashMap<>();
        this.incidenceMatrix.put(edge.getStartvertex(), new HashMap<>());
        this.incidenceMatrix.put(edge.getEndvertex(), new HashMap<>());
        this.incidenceMatrix.get(edge.getStartvertex()).put(edge, edge.getWeight());
        this.incidenceMatrix.get(edge.getEndvertex()).put(edge, -edge.getWeight());
    }

    /**
     * Конструктор.
     * listEdge - список рёбер.
     * listVertex - список вершин.
     */
    public IncidenceMatrix(ArrayList<Edge<T>> listEdge, ArrayList<Vertex<T>> listVertex) {
        super(listEdge, listVertex);
        this.incidenceMatrix = new HashMap<>();
        for (var vertex : listVertex) {
            this.incidenceMatrix.put(vertex, new HashMap<>());
        }
        for (var edge : listEdge) {
            if (!this.incidenceMatrix.containsKey(edge.getEndvertex())) {
                this.incidenceMatrix.put(edge.getEndvertex(), new HashMap<>());
            }
            if (!this.incidenceMatrix.containsKey(edge.getStartvertex())) {
                this.incidenceMatrix.put(edge.getStartvertex(), new HashMap<>());
            }
            for (var v : this.incidenceMatrix.keySet()) {
                this.incidenceMatrix.get(v).put(edge, 0);
            }
            this.incidenceMatrix.get(edge.getStartvertex()).put(edge, edge.getWeight());
            this.incidenceMatrix.get(edge.getEndvertex()).put(edge, -edge.getWeight());
        }
    }

    /**
     * Getter для матрицы инцидентности.
     */
    public HashMap<Vertex<T>, HashMap<Edge<T>, Integer>> getIncidenceMatrix() {
        return this.incidenceMatrix;
    }

    /**
     * переписываем readFromfile унаследованный из Graph.
     */
    @Override
    public IncidenceMatrix<T> readFromfile(String filename) {
        super.readFromfile(filename);
        return new IncidenceMatrix<>(this.listofEdges, this.listofVertexs);
    }

    /**
     * добавление ребра в матрицу инцидентности.
     */
    @Override
    public void addEdge(Edge<T> edge) {
        addVertex(edge.getEndvertex());
        addVertex(edge.getStartvertex());
        for (var v : this.incidenceMatrix.keySet()) {
            this.incidenceMatrix.get(v).put(edge, 0);
        }
        var x = this.incidenceMatrix.get(edge.getStartvertex());
        x.put(edge, edge.getWeight());
        var y = this.incidenceMatrix.get(edge.getEndvertex());
        y.put(edge, -edge.getWeight());
    }

    /**
     * добавление вершины.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        if (!this.incidenceMatrix.containsKey(vertex)) {
            this.incidenceMatrix.put(vertex, new HashMap<>());
            for (var v : this.incidenceMatrix.keySet()) {
                for (var edge : this.incidenceMatrix.get(v).keySet()) {
                    this.incidenceMatrix.get(vertex).put(edge, 0);
                }
            }
        }
    }

    /**
     * удаление ребра.
     */
    @Override
    public void removeEdge(Edge<T> edge) {
        for (var v : this.incidenceMatrix.keySet()) {
            this.incidenceMatrix.get(v).remove(edge);
        }
    }

    /**
     * удаление вершины.
     */
    @Override
    public void removeVertex(Vertex<T> vertex) {
        for (var edge : this.incidenceMatrix.get(vertex).keySet()) {
            if (vertex.equals(edge.getStartvertex())) {
                for (var v : this.incidenceMatrix.keySet()) {
                    if (!v.equals(vertex)) {
                        this.incidenceMatrix.get(v).remove(edge);
                    }
                }
            }
            if (vertex.equals(edge.getEndvertex())) {
                for (var v : this.incidenceMatrix.keySet()) {
                    if (!v.equals(vertex)) {
                        this.incidenceMatrix.get(v).remove(edge);
                    }
                }
            }
        }
        this.incidenceMatrix.remove(vertex);
    }

    /**
     * изменение веса ребра.
     */
    @Override
    public void changeWeight(Edge<T> edge, int newValue) {
        this.incidenceMatrix.get(edge.getStartvertex()).put(edge, newValue);
        this.incidenceMatrix.get(edge.getEndvertex()).put(edge, -newValue);
    }

    /**
     * нахождение кратчайшего пути.
     */
    @Override
    public ArrayList<Vertex<T>> shortestPath(Vertex<T> vertex) {
        ArrayList<Vertex<T>> result = new ArrayList<>();
        ArrayList<Vertex<T>> notVisited = new ArrayList<>();
        ArrayList<Vertex<T>> visited = new ArrayList<>();
        vertex.changeDist(0);
        result.add(vertex);
        notVisited.add(vertex);
        while (!notVisited.isEmpty()) {
            var v = notVisited.get(0);
            if (visited.contains(v)) {
                notVisited.remove(v);
                continue;
            }
            for (var edge : this.incidenceMatrix.get(v).keySet()) {
                if (this.incidenceMatrix.get(v).get(edge) > 0) {
                    notVisited.add(edge.getEndvertex());
                    if (result.contains(edge.getEndvertex())) {
                        if (edge.getEndvertex().getDist() >
                                this.incidenceMatrix.get(v).get(edge) + v.getDist()) {
                            edge.getEndvertex().changeDist(
                                    this.incidenceMatrix.get(v).get(edge) + v.getDist());
                        }
                    } else {
                        edge.getEndvertex().changeDist(
                                this.incidenceMatrix.get(v).get(edge) + v.getDist());
                        result.add(edge.getEndvertex());
                    }
                }
            }
            notVisited.remove(v);
            visited.add(v);
        }
        Collections.sort(result);
        return result;
    }

    /**
     * Кратчайший путь в виде строки.
     */
    public String shortestPathString(Vertex<T> vertex) {
        StringBuilder str = new StringBuilder();
        for (var v : this.shortestPath(vertex)) {
            str.append(v.getValue()).append(" ").append(v.getDist()).append("\n");
        }
        return str.toString();
    }

    /**
     * преобразуем матрицу смежности в читаемый вид.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("    ");
        for (var vertex : this.incidenceMatrix.keySet()) {
            for (var edge : this.incidenceMatrix.get(vertex).keySet()) {
                str.append(edge.getStartvertex().getValue()).append("->")
                        .append(edge.getEndvertex().getValue()).append("  ");
            }
            break;
        }
        for (var vertex : this.incidenceMatrix.keySet()) {
            str.append("\n");
            str.append(vertex.getValue()).append("     ");
            for (var edge : this.incidenceMatrix.get(vertex).keySet()) {
                str.append(this.incidenceMatrix.get(vertex).get(edge)).append("\t\t");
            }
        }
        return str.toString();
    }
}