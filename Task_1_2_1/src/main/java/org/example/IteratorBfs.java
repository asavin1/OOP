package org.example;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Поиск в ширину.
 */

public class IteratorBfs<T> implements Iterator<T> {
    private final Tree<T> startTree; //изначальное дерево.
    private final int numberOfNodes;  //количество вершин.
    private final Queue<Tree<T>> queue;  //очередь.

    /**
     * Конструктор.
     */
    public IteratorBfs(Tree<T> node) {
        this.queue = new LinkedList<>();
        this.queue.add(node);
        this.numberOfNodes = node.numberOfTreeNodes();
        this.startTree = node;
    }

    /**
     * Проверка есть ли следующий элемент.
     */
    @Override
    public boolean hasNext() {
        //ConcurrentModificationException - это исключение, которое возникает,
        // когда коллекция модифицируется (т.е. изменяется) во время итерации по ней.
        // мы проверяем изменилось ли у нас количество вершин.
        if (this.numberOfNodes != this.startTree.numberOfTreeNodes()) {
            throw new ConcurrentModificationException();
        }
        return !this.queue.isEmpty(); //проверяем не пуста ли очередь.
    }

    /**
     * Поиск следующего элемента.
     */
    @Override
    public T next() {
        if (hasNext()) {
            Tree<T> next = this.queue.remove(); //удаляем вершину из очереди.
            this.queue.addAll(next.getChildren()); //добавляем детей в очередь.
            return next.getRoot();  //возвращаем значение вершины.
        }
        //NoSuchElementException - это исключение, которое указывает на то,
        // что в определенной ситуации не удалось найти ожидаемый элемент.
        throw new NoSuchElementException();
    }
}
