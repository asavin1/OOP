package org.example;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Поиск в глубину.
 */
public class IteratorDfs<T> implements Iterator<T> {
    private final Stack<Tree<T>> stack;  //стек.
    private final int numberOfNodes;  //количество вершин.
    private final Tree<T> startTree;  //изначальное дерево.


    public IteratorDfs(Tree<T> tree) {
        this.stack = new Stack<>();
        stack.push(tree);
        this.numberOfNodes = tree.numberOfTreeNodes();
        this.startTree = tree;
    }

    /**
     * Проверка есть ли следующий элемент.
     */
    @Override
    public boolean hasNext() {
        //ConcurrentModificationException - это исключение, которое возникает,
        // когда коллекция модифицируется (т.е. изменяется) во время итерации по ней.
        // мы проверяем изменилось ли у нас количество вершин.
        if (this.numberOfNodes != startTree.numberOfTreeNodes()) {
            throw new ConcurrentModificationException();
        }
        return !this.stack.isEmpty(); //проверяем не пуста ли очередь.
    }

    /**
     * Поиск следующего элемента.
     */
    @Override
    public T next() {
        if (hasNext()) {
            Tree<T> next = this.stack.pop();  //достаём вершину из очереди.
            this.stack.addAll(next.getChildren()); //добавляем детей в стек.
            return next.getRoot(); //возвращаем значение вершины.
        }
        //NoSuchElementException - это исключение, которое указывает на то,
        // что в определенной ситуации не удалось найти ожидаемый элемент.
        throw new NoSuchElementException();
    }
}
