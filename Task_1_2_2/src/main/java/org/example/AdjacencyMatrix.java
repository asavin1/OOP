package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Матрица смежности.
 */
public class AdjacencyMatrix<T> extends Graph<T> {
    private final HashMap<Vertex<T>, HashMap<Vertex<T>, Integer>> adjacencyMatrix;

    /**
     * Конструктор.
     * start - начальная вершина.
     * end - конечная вершина.
     * weight - вес ребра.
     */
    public AdjacencyMatrix(Vertex<T> start, Vertex<T> end, int weight) {
        //Вызов конструктора суперкласса с передачей параметров start, end и weight
        super(start, end, weight);
        this.adjacencyMatrix = new HashMap<>();
        // Добавление в adjacencyMatrix
        // новых элементов с ключами start и end и значениями в виде пустых HashMap
        this.adjacencyMatrix.put(start, new HashMap<>());
        this.adjacencyMatrix.put(end, new HashMap<>());
        // Добавление во внутренний HashMap, соответствующий ключу start,
        // нового элемента с ключом end и значением weight
        this.adjacencyMatrix.get(start).put(end, weight);
        this.adjacencyMatrix.get(end).put(start, 0);
        this.adjacencyMatrix.get(start).put(start, 0);
        this.adjacencyMatrix.get(end).put(end, 0);
    }


    /**
     * Конструктор.
     * edge - ребро.
     */
    public AdjacencyMatrix(Edge<T> edge) {
        // Вызов конструктора суперкласса с передачей параметра edge
        super(edge);
        this.adjacencyMatrix = new HashMap<>();
        // Добавление в adjacencyMatrix
        // новых элементов с ключами start и end и значениями в виде пустых HashMap
        this.adjacencyMatrix.put(edge.getStartvertex(), new HashMap<>());
        this.adjacencyMatrix.put(edge.getEndvertex(), new HashMap<>());
        // Получение внутреннего HashMap, соответствующего ключу startvertex,
        // и добавление нового элемента с ключом endvertex и значением edge.getWeight()
        var x = this.adjacencyMatrix.get(edge.getStartvertex());
        x.put(edge.getEndvertex(), edge.getWeight());
        x.put(edge.getStartvertex(), 0);
        x = this.adjacencyMatrix.get(edge.getEndvertex());
        x.put(edge.getStartvertex(), 0);
        x.put(edge.getEndvertex(), 0);
    }


    /**
     * Конструктор.
     * listEdge - список рёбер.
     * listVertex - список вершин.
     */
    public AdjacencyMatrix(ArrayList<Edge<T>> listEdge, ArrayList<Vertex<T>> listVertex) {
        // Вызов конструктора суперкласса с передачей параметров listEdge и listVertex
        super(listEdge, listVertex);
        this.adjacencyMatrix = new HashMap<>();
        // Добавление в adjacencyMatrix новых элементов с ключами из listVertex
        // и значениями в виде пустых HashMap
        for (var vertex : listVertex) {
            this.adjacencyMatrix.put(vertex, new HashMap<>());
        }
        // Для каждой пары вершин из listVertex добавляем элементы
        // во внутренние HashMap с ключами из listVertex и значениями 0
        for (var vertex : listVertex) {
            for (var vertex2 : listVertex) {
                this.adjacencyMatrix.get(vertex).put(vertex2, 0);
                this.adjacencyMatrix.get(vertex2).put(vertex, 0);
            }
        }
        for (var edge : listEdge) {
            // если в матрице смежности нет начальной вершины ребра, то добавляем
            if (!this.adjacencyMatrix.containsKey(edge.getStartvertex())) {
                this.adjacencyMatrix.put(edge.getStartvertex(), new HashMap<>());
                // Для каждой вершины из adjacencyMatrix добавляем элементы
                // во внутренние HashMap с ключами startvertex ребра и значениями 0
                for (var vertex : this.adjacencyMatrix.keySet()) {
                    this.adjacencyMatrix.get(vertex).put(edge.getStartvertex(), 0);
                }
            }
            // если в матрице смежности нет конечной вершины ребра, то добавляем
            if (!this.adjacencyMatrix.containsKey(edge.getEndvertex())) {
                this.adjacencyMatrix.put(edge.getEndvertex(), new HashMap<>());
                // Для каждой вершины из adjacencyMatrix добавляем элементы
                // во внутренние HashMap с ключами endvertex ребра и значениями 0
                for (var vertex : this.adjacencyMatrix.keySet()) {
                    this.adjacencyMatrix.get(vertex).put(edge.getEndvertex(), 0);
                }
            }
            // Получаем внутренний HashMap, соответствующий ключу startvertex ребра,
            // и добавляем новый элемент с ключом endvertex ребра и значением edge.getWeight()
            var x = this.adjacencyMatrix.get(edge.getStartvertex());
            x.put(edge.getEndvertex(), edge.getWeight());
        }
    }

    /**
     * Getter для матрицы смежности.
     */
    public HashMap<Vertex<T>, HashMap<Vertex<T>, Integer>> getAdjacencymatrix() {
        return this.adjacencyMatrix;
    }

    /**
     * переписываем readFromfile унаследованный из Graph.
     */
    @Override
    public AdjacencyMatrix<T> readFromfile(String filename) {
        super.readFromfile(filename);
        return new AdjacencyMatrix<>(this.listofEdges, this.listofVertexs);
    }

    /**
     * добавление ребра в матрицу смежности.
     */
    @Override
    public void addEdge(Edge<T> edge) {
        addVertex(edge.getEndvertex());
        addVertex(edge.getStartvertex());
        var x = this.adjacencyMatrix.get(edge.getStartvertex());
        x.put(edge.getEndvertex(), edge.getWeight());
    }

    /**
     * добавление вершины.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        if (!this.adjacencyMatrix.containsKey(vertex)) {
            this.adjacencyMatrix.put(vertex, new HashMap<>());
            for (var v : this.adjacencyMatrix.keySet()) {
                this.adjacencyMatrix.get(v).put(vertex, 0);
                this.adjacencyMatrix.get(vertex).put(v, 0);
            }
        }
    }

    /**
     * удаление ребра.
     */
    @Override
    public void removeEdge(Edge<T> edge) {
        this.adjacencyMatrix.get(edge.getStartvertex()).put(edge.getEndvertex(), 0);
    }

    /**
     * удаление вершины.
     */
    @Override
    public void removeVertex(Vertex<T> vertex) {
        for (var v : this.adjacencyMatrix.keySet()) {
            this.adjacencyMatrix.get(v).remove(vertex);
        }
        this.adjacencyMatrix.remove(vertex);
    }

    /**
     * изменение веса ребра.
     */
    @Override
    public void changeWeight(Edge<T> edge, int newValue) {
        this.adjacencyMatrix.get(edge.getStartvertex()).put(edge.getEndvertex(), newValue);
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
            notVisited.addAll(this.adjacencyMatrix.get(v1).keySet());
            //бежим по списку смежных вершин.
            for (var v2 : this.adjacencyMatrix.get(v1).keySet()) {
                if (this.adjacencyMatrix.get(v1).get(v2) != 0) {
                    //если v2 уже добавлен в результат.
                    if (result.contains(v2)) {
                        //если дистанция до v2 больше чем до дистанция до v1 + вес ребра v1 - v2.
                        if (v2.getDist() > this.adjacencyMatrix.get(v1).get(v2) + v1.getDist()) {
                            //то мы меняем дистанцию v2 на дистанция до v1 + вес ребра v1 - v2.
                            v2.changeDist(this.adjacencyMatrix.get(v1).get(v2) + v1.getDist());
                        }
                    } else {
                        //меняем дистанцию v2 на дистанция до v1 + вес ребра v1 - v2.
                        v2.changeDist(this.adjacencyMatrix.get(v1).get(v2) + v1.getDist());
                        result.add(v2);
                    }
                }
            }
            notVisited.remove(v1);
            visited.add(v1);
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
        str.append("  ");
        for (var vertex1 : this.adjacencyMatrix.keySet()) {
            str.append(vertex1.getValue()).append(" ");
        }
        for (var vertex1 : this.adjacencyMatrix.keySet()) {
            str.append("\n");
            str.append(vertex1.getValue()).append(" ");
            for (var vertex2 : this.adjacencyMatrix.keySet()) {
                str.append(this.adjacencyMatrix.get(vertex1).get(vertex2)).append("  ");
            }
        }
        return str.toString();
    }
}
