package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестируем.
 */
public class AdjacencyListTest {
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
        var readGraph = new AdjacencyList<String>(new ArrayList<>(), new ArrayList<>());
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
        actual.get(v1).put(v2, 1);
        actual.get(v2).put(v3, 4);
        actual.get(v2).put(v4, 2);

        assertEquals(readGraph.getAdjacencylist(), actual);
    }

    /**
     * Тестируем чтение с файла.
     * файл:
     * 0
     */
    @Test
    public void testRead0() {
        var readGraph = new AdjacencyList<String>(new ArrayList<>(), new ArrayList<>());
        readGraph = readGraph.readFromfile("src/test/resources/test2.txt");
        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        assertEquals(readGraph.getAdjacencylist(), actual);
    }

    /**
     * Тестируем добавление ребра.
     */
    @Test
    public void testAddedge() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var adjacencyList = new AdjacencyList<>(v1, v2, 1);
        adjacencyList.addEdge(new Edge<>(v2, v3, 1));

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(v2, 1);
        actual.get(v2).put(v3, 1);

        assertEquals(adjacencyList.getAdjacencylist(), actual);
    }

    /**
     * Тестируем добавление вершины.
     */
    @Test
    public void testAddvertex() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var adjacencyList = new AdjacencyList<>(v1, v2, 1);
        adjacencyList.addVertex(v3);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(v2, 1);

        assertEquals(adjacencyList.getAdjacencylist(), actual);
    }

    /**
     * Тестируем удаление ребра.
     */
    @Test
    public void testRemoveedge() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var edge = new Edge<>(v1, v2, 1);
        var adjacencyList = new AdjacencyList<>(edge);
        adjacencyList.addEdge(new Edge<>(v2, v3, 1));
        adjacencyList.removeEdge(edge);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v2).put(v3, 1);

        assertEquals(adjacencyList.getAdjacencylist(), actual);
    }

    /**
     * Тестируем удаление вершины.
     */
    @Test
    public void testRemovevertex() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var adjacencyList = new AdjacencyList<>(v1, v2,  1);
        adjacencyList.addEdge(new Edge<>(v2, v3, 1));
        adjacencyList.removeVertex(v2);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v3, new HashMap<>());

        assertEquals(adjacencyList.getAdjacencylist(), actual);
    }

    /**
     * Тестируем изменение веса ребра.
     */
    @Test
    public void testChangeweight() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var g = new AdjacencyList<>(v1, v2, 1);
        var edge = new Edge<>(v2, v3, 1);
        g.addEdge(edge);
        g.changeWeight(edge, 10);

        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(v2, 1);
        actual.get(v2).put(v3, 10);

        assertEquals(g.getAdjacencylist(), actual);
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
        var adjacencyList = new AdjacencyList<>(v1, v2, 7);
        adjacencyList.addEdge(new Edge<>(v1, v4, 3));
        adjacencyList.addEdge(new Edge<>(v4, v2, 2));
        adjacencyList.addEdge(new Edge<>(v4, v3, 5));
        adjacencyList.addEdge(new Edge<>(v5, v1, 10));

        var actual = new ArrayList<>();
        actual.add(v1);
        actual.add(v4);
        actual.add(v2);
        actual.add(v3);

        assertEquals(adjacencyList.shortestPath(v1), actual);
        assertEquals(adjacencyList.shortestPath(v1).get(0).getDist(), 0);
        assertEquals(adjacencyList.shortestPath(v1).get(2).getDist(), 5);
        assertEquals(adjacencyList.shortestPath(v1).get(1).getDist(), 3);
        assertEquals(adjacencyList.shortestPath(v1).get(3).getDist(), 8);
    }

    /**
     * Тестируем кратчайший путь как строку.
     */
    @Test
    public void testShortestPathString() {
        var v1 = new Vertex<>("v1");
        var v2 = new Vertex<>("v2");
        var v3 = new Vertex<>("v3");
        var v4 = new Vertex<>("v4");
        var v5 = new Vertex<>("v5");
        var adjacencyList = new AdjacencyList<>(v1, v2, 7);
        adjacencyList.addEdge(new Edge<>(v1, v4, 3));
        adjacencyList.addEdge(new Edge<>(v4, v2, 2));
        adjacencyList.addEdge(new Edge<>(v4, v3, 5));
        adjacencyList.addEdge(new Edge<>(v5, v1, 10));

        var str = "v1 0\nv2 5\nv4 3\nv3 8\n";

        assertEquals(adjacencyList.shortestPathstring(v1), str);
    }
}
