package org.example;

import java.util.Iterator;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Тестим.
 */
public class TreeTest {
    /**
     * Тестируем добавление вершины.
     */
    @Test
    void testAddChild() {
        Tree<String> tree = new Tree<>("A");
        tree.addChild("B");
        assertEquals(tree.toString(), "A  B  ");
    }

    /**
     * Тестируем добавление поддерева.
     */
    @Test
    void testAddSubtree() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> subtree = new Tree<>("B");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        assertEquals(tree.toString(), "A  B  C  D  ");
    }

    /**
     * Тестируем удаление поддерева.
     */
    @Test
    void testDeleteSubtree() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> subtree = new Tree<>("B");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        subtree.deleteSubTree();
        assertEquals(tree.toString(), "A  ");
    }

    /**
     * Тестируем удаление элемента, у которого есть родителей и нет детей.
     */
    @Test
    void testDeleteNode() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> subtree = new Tree<>("B");
        tree.addChild(subtree);
        subtree.deleteNode();
        assertEquals(tree.toString(), "A  ");
    }

    /**
     * Тестируем удаление элемента, у которого один ребёнок.
     */
    @Test
    void testDeleteNode2() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> subtree = new Tree<>("B");
        subtree.addChild("C");
        tree.addChild(subtree);
        subtree.deleteNode();
        assertEquals(tree.toString(), "A  C  ");
    }

    /**
     * Тестируем удаление элемента, у которого больше одного ребёнка.
     */
    @Test
    void testDeleteNode3() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> subtree = new Tree<>("B");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        subtree.deleteNode();
        assertEquals(tree.toString(), "A  C  D  ");
    }

    /**
     * Тестируем подсчёт вершин.
     */
    @Test
    void testNumberNodes() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> subtree = new Tree<>("B");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        var a = tree.numberOfTreeNodes();
        assertEquals(a, 4);
    }

    /**
     * Тестируем Dfs.
     */
    @Test
    void testDfs() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> subtree = new Tree<>("B");
        tree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        StringBuilder res = new StringBuilder();

        Iterator<String> dfs = tree.getIteratorDFS();
        while (dfs.hasNext()) {
            res.append(dfs.next());
        }
        assertEquals("ABDC", res.toString());
    }

    /**
     * Тестируем Bfs.
     */
    @Test
    void testBfs() {
        Tree<String> tree = new Tree<>("A");
        Tree<String> subtree = new Tree<>("B");
        tree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        StringBuilder res = new StringBuilder();

        Iterator<String> dfs = tree.getIteratorBFS();
        while (dfs.hasNext()) {
            res.append(dfs.next());
        }
        assertEquals("ACBD", res.toString());
    }
}
