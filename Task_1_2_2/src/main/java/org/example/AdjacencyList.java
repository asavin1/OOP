package org.example;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Список смежности.
 */
public class AdjacencyList<T> extends Graph<T> {

    /*
     * Ключами во внешнем HashMap являются объекты типа Vertex<T>,
     * являющиеся вершинами графа, а значениями являются другие HashMap'ы,
     * которые представляют собой список смежности для каждой вершины.
     * Во внутренних HashMap'ах ключами также являются объекты типа Vertex<T>,
     * а значениями - целочисленные веса ребер, представляющие веса ребер между вершинами.
     */
    private final HashMap<Vertex<T>, HashMap<Vertex<T>, Integer>> adjacencyList;

    /**
     * Конструктор.
     * start - стартовая вершина ребра
     * end - конечная вершина ребра.
     * weight - вес нового ребра.
     */
    public AdjacencyList(Vertex<T> start, Vertex<T> end, int weight) {
        //вызываем конструктор суперкласса Edge с передачей ему параметров start, end и weight.
        super(start, end, weight);
        //создаем новый объект HashMap и присваиваем полю adjacencyList.
        this.adjacencyList = new HashMap<>();
        //в этот HashMap добавляем две записи с ключами start и end.
        this.adjacencyList.put(start, new HashMap<>());
        this.adjacencyList.put(end, new HashMap<>());
        //Затем в HashMap, связанный с ключом start, добавляется запись,
        //в которой ключом является вершина end, а значением - вес ребра weight.
        this.adjacencyList.get(start).put(end, weight);
    }

    /**
     * Конструктор.
     * edge - ребро.
     */
    public AdjacencyList(Edge<T> edge) {
        //вызываем конструктор суперкласса Edge.
        super(edge);
        //создаем новый объект HashMap и присваиваем полю adjacencyList.
        this.adjacencyList = new HashMap<>();
        //в этот HashMap добавляем две записи с ключами start и end.
        this.adjacencyList.put(edge.getStartvertex(), new HashMap<>());
        this.adjacencyList.put(edge.getEndvertex(), new HashMap<>());
        //Затем в HashMap, связанный с ключом начальной вершины, добавляется запись,
        //в которой ключом является конечная вершина, а значением - вес ребра weight.
        this.adjacencyList.get(edge.getStartvertex()).put(edge.getEndvertex(), edge.getWeight());
    }


    /**
     * Конструктор.
     * listEdge - список рёбер.
     * listVertex - список вершин.
     */
    public AdjacencyList(ArrayList<Edge<T>> listEdge, ArrayList<Vertex<T>> listVertex) {
        super(listEdge, listVertex);
        this.adjacencyList = new HashMap<>();
        //сначала для каждой вершины создаём пустой HashMap.
        for (var vertex : listVertex) {
            this.adjacencyList.put(vertex, new HashMap<>());
        }
        //идём по рёбрам смотрим есть ли вершины в HashMap и если нет добавляем.
        for (var edge : listEdge) {
            if (!this.adjacencyList.containsKey(edge.getStartvertex())) {
                this.adjacencyList.put(edge.getStartvertex(), new HashMap<>());
            }
            if (!this.adjacencyList.containsKey(edge.getEndvertex())) {
                this.adjacencyList.put(edge.getEndvertex(), new HashMap<>());
            }
            this.adjacencyList.get(edge.getStartvertex())
                    .put(edge.getEndvertex(), edge.getWeight());
        }
    }

    /**
     * Getter для списка смежности.
     */
    public HashMap<Vertex<T>, HashMap<Vertex<T>, Integer>> getAdjacencylist() {
        return this.adjacencyList;
    }

    /**
     * переписываем readFromfile унаследованный из Graph.
     */
    @Override
    public AdjacencyList<T> readFromfile(String filename) {
        //вызывем readFromfile из Graph чтобы получить список верщин и рёбер.
        super.readFromfile(filename);
        return new AdjacencyList<>(this.listofEdges, this.listofVertexs);
    }

    /**
     * добавление ребра в список смежности.
     */
    @Override
    public void addEdge(Edge<T> edge) {
        addVertex(edge.getStartvertex());
        addVertex(edge.getEndvertex());
        this.adjacencyList.get(edge.getStartvertex()).put(edge.getEndvertex(), edge.getWeight());
    }

    /**
     * добавление вершины.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        //если её нет, то добавляем.
        if (!this.adjacencyList.containsKey(vertex)) {
            this.adjacencyList.put(vertex, new HashMap<>());
        }
    }

    /**
     * удаление ребра.
     */
    @Override
    public void removeEdge(Edge<T> edge) {
        this.adjacencyList.get(edge.getStartvertex()).remove(edge.getEndvertex());
        if (this.adjacencyList.get(edge.getStartvertex()).isEmpty()) {
            removeVertex(edge.getStartvertex());
        }
        if (this.adjacencyList.get(edge.getEndvertex()).isEmpty()) {
            removeVertex(edge.getEndvertex());
        }
    }

    /**
     * удаление вершины.
     */
    @Override
    public void removeVertex(Vertex<T> vertex) {
        for (var v : this.adjacencyList.keySet()) {
            this.adjacencyList.get(v).remove(vertex);
        }
        this.adjacencyList.remove(vertex);
    }

    /**
     * изменение веса ребра.
     */
    @Override
    public void changeWeight(Edge<T> edge, int newValue) {
        this.adjacencyList.get(edge.getStartvertex()).put(edge.getEndvertex(), newValue);
    }

    /**
     * нахождение кратчайшего пути.
     */
    @Override
    public ArrayList<Vertex<T>> shortestPath(Vertex<T> vertex) {
        ArrayList<Vertex<T>> result = new ArrayList<>();
        ArrayList<Vertex<T>> notVisited = new ArrayList<>();
        ArrayList<Vertex<T>> visited = new ArrayList<>();
        //меняем дистанцию от нашей вершину на 0.
        vertex.changeDist(0);
        //добавляем в результат.
        result.add(vertex);
        //добавляем в список непосещённых вершин.
        notVisited.add(vertex);
        //пока список не посещённых не опустеет
        while (!notVisited.isEmpty()) {
            //берём первую из этого списка.
            var v1 = notVisited.get(0);
            //если вершина уже посещена, то удаляем её из списка непосещённых.
            if (visited.contains(v1)) {
                notVisited.remove(v1);
                continue;
            }
            //добавляем к непосещённым вершинам все врершины смежные с v1.
            notVisited.addAll(this.adjacencyList.get(v1).keySet());
            //бежим по списку смежных вершин.
            for (var v2 : this.adjacencyList.get(v1).keySet()) {
                //если v2 уже добавлен в результат.
                if (result.contains(v2)) {
                    //если дистанция до v2 больше чем до дистанция до v1 + вес ребра v1 - v2.
                    if (v2.getDist() > this.adjacencyList.get(v1).get(v2) + v1.getDist()) {
                        //то мы меняем дистанцию v2 на дистанция до v1 + вес ребра v1 - v2.
                        v2.changeDist(this.adjacencyList.get(v1).get(v2) + v1.getDist());
                    }
                } else {
                    //меняем дистанцию v2 на дистанция до v1 + вес ребра v1 - v2.
                    v2.changeDist(this.adjacencyList.get(v1).get(v2) + v1.getDist());
                    result.add(v2);
                }
            }
            notVisited.remove(v1);
            visited.add(v1);
        }

        return result;
    }

    /**
     * Кратчайший путь в виде строки.
     */
    public String shortestPathstring(Vertex<T> vertex) {
        StringBuilder str = new StringBuilder();
        for (var v : this.shortestPath(vertex)) {
            str.append(v.getValue()).append(" ").append(v.getDist()).append("\n");
        }
        return str.toString();
    }

    /**
     * Преобразуем список смежности в читаемый вид.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (var vertex1 : this.adjacencyList.keySet()) {
            str.append(vertex1.getValue()).append(" ");

            for (var vertex2 : this.adjacencyList.get(vertex1).keySet()) {
                str.append("(");
                str.append(vertex2.getValue()).append(", ");
                str.append(this.adjacencyList.get(vertex1).get(vertex2));
                str.append(");  ");
            }
            str.append("\n");
        }
        return str.toString();
    }
}
