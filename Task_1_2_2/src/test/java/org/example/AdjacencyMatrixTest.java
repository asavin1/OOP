package org.example;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестируем.
 */
public class AdjacencyMatrixTest {
    /**
     * Тестируем чтение с файла.
     * файл:
     * 3
     * v1 v2 1
     * v2 v3 4
     * v2 v4 2
     */
    @Test
    public void testRead() {
        var readGraph = new AdjacencyMatrix<String>(new ArrayList<>(), new ArrayList<>());
        readGraph = readGraph.readFromfile("src/test/resources/test1.txt");
        var v1 = (new Vertex<>("v1")).addtoList(readGraph.listofVertexs);
        var v2 = (new Vertex<>("v2")).addtoList(readGraph.listofVertexs);
        var v3 = (new Vertex<>("v3")).addtoList(readGraph.listofVertexs);
        var v4 = (new Vertex<>("v4")).addtoList(readGraph.listofVertexs);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.put(v4, new HashMap<>());

        actual.get(v1).put(v1, 0);
        actual.get(v1).put(v2, 1);
        actual.get(v1).put(v3, 0);
        actual.get(v1).put(v4, 0);

        actual.get(v2).put(v1, 0);
        actual.get(v2).put(v2, 0);
        actual.get(v2).put(v3, 4);
        actual.get(v2).put(v4, 2);

        actual.get(v3).put(v1, 0);
        actual.get(v3).put(v2, 0);
        actual.get(v3).put(v3, 0);
        actual.get(v3).put(v4, 0);

        actual.get(v4).put(v1, 0);
        actual.get(v4).put(v2, 0);
        actual.get(v4).put(v3, 0);
        actual.get(v4).put(v4, 0);

        assertEquals(readGraph.getAdjacencymatrix(), actual);
    }

    /**
     * Тестируем чтение с файла.
     * файл:
     * 0
     */
    @Test
    public void testRead0() {
        var readGraph = new AdjacencyMatrix<>(new ArrayList<>(), new ArrayList<>());
        readGraph = readGraph.readFromfile("src/test/resources/test2.txt");
        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        assertEquals(readGraph.getAdjacencymatrix(), actual);
    }

    /**
     * Тестируем добавление ребра.
     */
    @Test
    public void testAddEdge() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var edge1 = new Edge<>(v1, v2, 1);
        var edge2 = new Edge<>(v1, v3, 1);
        var adjacencyMatrix = new AdjacencyMatrix<>(edge1);
        adjacencyMatrix.addEdge(edge2);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(v1, 0);
        actual.get(v1).put(v2, 1);
        actual.get(v1).put(v3, 1);

        actual.get(v2).put(v1, 0);
        actual.get(v2).put(v2, 0);
        actual.get(v2).put(v3, 0);

        actual.get(v3).put(v1, 0);
        actual.get(v3).put(v2, 0);
        actual.get(v3).put(v3, 0);

        assertEquals(adjacencyMatrix.getAdjacencymatrix(), actual);
    }

    /**
     * Тестируем добавление вершины.
     */
    @Test
    public void testAddVertex() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var edge1 = new Edge<>(v1, v2, 1);
        var adjacencyMatrix = new AdjacencyMatrix<>(edge1);
        adjacencyMatrix.addVertex(v3);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(v1, 0);
        actual.get(v1).put(v2, 1);
        actual.get(v1).put(v3, 0);

        actual.get(v2).put(v1, 0);
        actual.get(v2).put(v2, 0);
        actual.get(v2).put(v3, 0);

        actual.get(v3).put(v1, 0);
        actual.get(v3).put(v2, 0);
        actual.get(v3).put(v3, 0);

        assertEquals(adjacencyMatrix.getAdjacencymatrix(), actual);
    }

    /**
     * Тестируем удаление ребра.
     */
    @Test
    public void testRemoveEdge() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var edge = new Edge<>(v1, v2, 1);
        var adjacencyMatrix = new AdjacencyMatrix<>(edge);
        var edge2 = new Edge<>(v1, v3, 1);
        adjacencyMatrix.addEdge(edge2);
        adjacencyMatrix.removeEdge(edge);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(v1, 0);
        actual.get(v1).put(v2, 0);
        actual.get(v1).put(v3, 1);

        actual.get(v2).put(v1, 0);
        actual.get(v2).put(v2, 0);
        actual.get(v2).put(v3, 0);

        actual.get(v3).put(v1, 0);
        actual.get(v3).put(v2, 0);
        actual.get(v3).put(v3, 0);

        assertEquals(adjacencyMatrix.getAdjacencymatrix(), actual);
    }

    /**
     * Тестируем удаление вершины.
     */
    @Test
    public void testRemoveVertex() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var edge = new Edge<>(v1, v2, 1);
        var adjacencyMatrix = new AdjacencyMatrix<>(edge);
        var edge2 = new Edge<>(v2, v3, 1);
        adjacencyMatrix.addEdge(edge2);
        adjacencyMatrix.removeVertex(v2);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v3, new HashMap<>());

        actual.get(v1).put(v1, 0);
        actual.get(v1).put(v3, 0);

        actual.get(v3).put(v1, 0);
        actual.get(v3).put(v3, 0);

        assertEquals(adjacencyMatrix.getAdjacencymatrix(), actual);
    }

    /**
     * Тестируем изменение веса ребра.
     */
    @Test
    public void testChangeValueEdge() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var edge = new Edge<>(v1, v2, 1);
        var adjacencyMatrix = new AdjacencyMatrix<>(edge);
        var edge1 = new Edge<>(v1, v3, 1);
        adjacencyMatrix.addEdge(edge1);
        adjacencyMatrix.changeWeight(edge1, 10);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(v1, 0);
        actual.get(v1).put(v2, 1);
        actual.get(v1).put(v3, 10);

        actual.get(v2).put(v1, 0);
        actual.get(v2).put(v2, 0);
        actual.get(v2).put(v3, 0);

        actual.get(v3).put(v1, 0);
        actual.get(v3).put(v2, 0);
        actual.get(v3).put(v3, 0);

        assertEquals(adjacencyMatrix.getAdjacencymatrix(), actual);
    }

    /**
     * Тестируем кратчайший путь.
     */
    @Test
    public void testShortestPath() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var v4 = new Vertex<>("v4");
        var v5 = new Vertex<>("v5");
        var adjacencyMatrix = new AdjacencyMatrix<>(v1, v2, 7);
        adjacencyMatrix.addEdge(new Edge<>(v1, v4, 3));
        adjacencyMatrix.addEdge(new Edge<>(v4, v2, 2));
        adjacencyMatrix.addEdge(new Edge<>(v4, v3, 5));
        adjacencyMatrix.addEdge(new Edge<>(v5, v1, 10));

        var actual = new ArrayList<>();
        actual.add(v1);
        actual.add(v4);
        actual.add(v2);
        actual.add(v3);

        assertEquals(adjacencyMatrix.shortestPath(v1), actual);
        assertEquals(adjacencyMatrix.shortestPath(v1).get(0).getDist(), 0);
        assertEquals(adjacencyMatrix.shortestPath(v1).get(1).getDist(), 3);
        assertEquals(adjacencyMatrix.shortestPath(v1).get(2).getDist(), 5);
        assertEquals(adjacencyMatrix.shortestPath(v1).get(3).getDist(), 8);
    }
}
