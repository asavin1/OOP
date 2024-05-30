package org.example.pizzeria;

import java.util.Queue;

/**
 * Interface для очереди.
 */
public interface ImyQueue<T> {

    /**
     * Push.
     */
    public abstract void push(T value) throws InterruptedException;

    /**
     * Pop.
     */
    public abstract T pop() throws InterruptedException;

    /**
     * Getter для очереди.
     */
    public abstract Queue<T> getQueue();

    /**
     * Setter для очереди.
     */
    public abstract void setQueue(Queue<T> queue);

    /**
     * получаем размер очереди.
     */
    public abstract int size();
}
