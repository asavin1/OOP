package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тестируем.
 */
public class IncidenceMatrixTest {
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
        var readGraph = new IncidenceMatrix<String>(new ArrayList<>(), new ArrayList<>());
        readGraph = readGraph.readFromfile("src/test/resources/test1.txt");
        var v1 = (new Vertex<>("v1")).addtoList(readGraph.listofVertexs);
        var v2 = (new Vertex<>("v2")).addtoList(readGraph.listofVertexs);
        var v3 = (new Vertex<>("v3")).addtoList(readGraph.listofVertexs);
        var v4 = (new Vertex<>("v4")).addtoList(readGraph.listofVertexs);

        HashMap<Vertex<String>, HashMap<Edge<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.put(v4, new HashMap<>());
        var edge1 = (new Edge<>(v1, v2, 1)).addtoList(readGraph.listofEdges);
        var edge2 = (new Edge<>(v2, v3, 4)).addtoList(readGraph.listofEdges);
        var edge3 = (new Edge<>(v2, v4, 2)).addtoList(readGraph.listofEdges);

        actual.get(v1).put(edge2, 0);
        actual.get(v1).put(edge1, 1);
        actual.get(v1).put(edge3, 0);


        actual.get(v2).put(edge2, 4);
        actual.get(v2).put(edge1, -1);
        actual.get(v2).put(edge3, 2);

        actual.get(v3).put(edge2, -4);
        actual.get(v3).put(edge1, 0);
        actual.get(v3).put(edge3, 0);

        actual.get(v4).put(edge2, 0);
        actual.get(v4).put(edge1, 0);
        actual.get(v4).put(edge3, -2);

        assertEquals(readGraph.getIncidenceMatrix(), actual);
    }

    /**
     * Тестируем чтение с файла.
     * файл:
     * 0
     */
    @Test
    public void testRead0() {
        var readGraph = new IncidenceMatrix<>(new ArrayList<>(), new ArrayList<>());
        readGraph = readGraph.readFromfile("src/test/resources/test2.txt");
        HashMap<Vertex<String>, HashMap<Vertex<String>, Integer>> actual = new HashMap<>();
        assertEquals(readGraph.getIncidenceMatrix(), actual);
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
        var edge2 = new Edge<>(v2, v3, 1);
        var incidenceMatrix = new IncidenceMatrix<>(edge1);
        incidenceMatrix.addEdge(edge2);

        HashMap<Vertex<String>, HashMap<Edge<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(edge1, 1);
        actual.get(v2).put(edge1, -1);
        actual.get(v2).put(edge2, 1);
        actual.get(v3).put(edge2, -1);
        actual.get(v1).put(edge2, 0);
        actual.get(v3).put(edge1, 0);

        assertEquals(incidenceMatrix.getIncidenceMatrix(), actual);
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
        var incidenceMatrix = new IncidenceMatrix<>(edge1);
        incidenceMatrix.addVertex(v3);

        HashMap<Vertex<String>, HashMap<Edge<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(edge1, 1);
        actual.get(v2).put(edge1, -1);
        actual.get(v3).put(edge1, 0);

        assertEquals(incidenceMatrix.getIncidenceMatrix(), actual);
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
        var incidenceMatrix = new IncidenceMatrix<>(edge);
        var edge2 = new Edge<>(v2, v3, 1);
        incidenceMatrix.addEdge(edge2);
        incidenceMatrix.removeEdge(edge);

        HashMap<Vertex<String>, HashMap<Edge<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v2).put(edge2, 1);
        actual.get(v3).put(edge2, -1);
        actual.get(v1).put(edge2, 0);

        assertEquals(incidenceMatrix.getIncidenceMatrix(), actual);
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
        var incidenceMatrix = new IncidenceMatrix<>(edge);
        var edge2 = new Edge<>(v2, v3, 1);
        incidenceMatrix.addEdge(edge2);
        incidenceMatrix.removeVertex(v2);

        HashMap<Vertex<String>, HashMap<Edge<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v3, new HashMap<>());

        assertEquals(incidenceMatrix.getIncidenceMatrix(), actual);
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
        var incidenceMatrix = new IncidenceMatrix<>(edge);
        var edge1 = new Edge<>(v2, v3, 1);
        incidenceMatrix.addEdge(edge1);
        incidenceMatrix.changeWeight(edge1, 10);

        HashMap<Vertex<String>, HashMap<Edge<String>, Integer>> actual = new HashMap<>();
        actual.put(v1, new HashMap<>());
        actual.put(v2, new HashMap<>());
        actual.put(v3, new HashMap<>());
        actual.get(v1).put(edge, 1);
        actual.get(v2).put(edge1, 10);
        actual.get(v2).put(edge, -1);
        actual.get(v3).put(edge1, -10);
        actual.get(v1).put(edge1, 0);
        actual.get(v3).put(edge, 0);

        assertEquals(incidenceMatrix.getIncidenceMatrix(), actual);
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
        var g = new IncidenceMatrix<>(7, v1, v2);
        g.addEdge(new Edge<>(v1, v4, 3));
        g.addEdge(new Edge<>(v4, v2, 2));
        g.addEdge(new Edge<>(v4, v3, 5));
        g.addEdge(new Edge<>(v5, v1, 10));

        var actual = new ArrayList<>();
        actual.add(v1);
        actual.add(v4);
        actual.add(v2);
        actual.add(v3);

        assertEquals(g.shortestPath(v1), actual);
        assertEquals(g.shortestPath(v1).get(0).getDist(), 0);
        assertEquals(g.shortestPath(v1).get(1).getDist(), 3);
        assertEquals(g.shortestPath(v1).get(2).getDist(), 5);
        assertEquals(g.shortestPath(v1).get(3).getDist(), 8);
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
        var g = new IncidenceMatrix<>(7, v1, v2);
        g.addEdge(new Edge<>(v1, v4, 3));
        g.addEdge(new Edge<>(v4, v2, 2));
        g.addEdge(new Edge<>(v4, v3, 5));
        g.addEdge(new Edge<>(v5, v1, 10));

        var str = "v1 0\nv4 3\nv2 5\nv3 8\n";

        assertEquals(g.shortestPathString(v1), str);
    }
}
