package org.example;


import java.util.*;

/**
 * Дерево.
 */
public class Tree<T> implements Iterable<T> {
    private T root;
    private Tree<T> parent;
    private ArrayList<Tree<T>> children;

    /**
     * Конструктор для дерева.
     */
    public Tree(T root) {
        this.root = root;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    /**
     * Getter для корня.
     */
    public T getRoot() {
        return this.root;
    }

    /**
     * Getter для детей.
     */
    public ArrayList<Tree<T>> getChildren() {
        return new ArrayList<>(this.children);
    }

    /**
     * Getter для родителя.
     */
    public Tree<T> getParent() {
        return this.parent;
    }

    /**
     * Добавление элемента.
     */
    public Tree<T> addChild(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Failed to add child with null value.");
        }
        Tree<T> child = new Tree<>(value);  //Заводим дерево.
        children.add(child);    //Добавляем новое дерево в список детей.
        child.parent = this;    //Отмечаем наше дерево как родителя.
        return child;
    }

    /**
     * Добавление поддерева.
     */
    public Tree<T> addChild(Tree<T> subTree) {
        if ((subTree == null) || (subTree.parent != null)) {
            throw new IllegalArgumentException("Failed to add null subtree" + " or parent already exists");
        }
        children.add(subTree);  //Добавляем поддерево в список детей.
        subTree.parent = this;  //Отмечаем наше дерево как родителя поддерева.
        return subTree;
    }


    /**
     * Удаление поддерева.
     */
    public void deleteSubTree() {
        Tree<T> parent = this.parent; //запоминаем родителя.
        this.children.clear(); //удаляем детей.
        if (parent != null) {   //если нет родителя, значит ничего больше не надо делать.
            //если родитель есть, значит удаляем наше дерево из списка его детей.
            parent.children.remove(this);
        }
    }

    /**
     * Удаление вершины.
     */
    public void deleteNode() {
        //Если у вершины есть родитель и нет детей.
        if (this.children.isEmpty() && this.parent != null) {
            this.parent.children.remove(this); //удаляем его из списка детей родителя.
        }
        //Если у вершины один ребёнок.
        if (this.children.size() == 1) {
            this.root = this.children.get(0).root;  //Подставляем в корень значение корня ребёнка.
            this.children = this.children.get(0).children;  //Подставляем в детей, детей ребёнка.
        }
        //Если у вершины больше одного ребёнка, то первый ребёнок становится вместо вершины.
        if (this.children.size() > 1) {
            //Подставляем в корень значение корня первого ребёнка.
            this.root = this.children.get(0).root;
            //добавляем к детям нашей вершины детей первого ребёнка.
            this.children.addAll(this.children.get(0).children);
            //удаляем из детей первого ребёнка.
            this.children.remove(0);
        }
    }


    /**
     * Количество вершин в дереве.
     */
    public int numberOfTreeNodes() {
        int number = 1;   //считаем саму вершину.
        for (var child : this.children) {
            number += child.numberOfTreeNodes();   //рекурсивно вызывем для всех детей.
        }
        return number;
    }

    /**
     * сравнение на равенство с другим деревом.
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        var tree = (Tree<?>) object;
        var bfs1 = tree.iterator();
        var bfs2 = this.iterator();
        while (bfs1.hasNext() && bfs2.hasNext()) {
            if (!bfs1.next().equals(bfs2.next())) {
                return false;
            }
        }
        if (bfs1.hasNext() != bfs2.hasNext()) {
            return false;
        }
        return true;
    }

    /**
     * iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new IteratorBfs<>(this);
    }


    /**
     * Getter для bfs.
     */
    public Iterator<T> getIteratorBFS() {
        return new IteratorBfs<>(this);
    }

    /**
     * Getter для dfs.
     */
    public Iterator<T> getIteratorDFS() {
        return new IteratorDfs<>(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (var elem : this) {
            str.append(elem);
            str.append("  ");
        }
        return str.toString();
    }
}