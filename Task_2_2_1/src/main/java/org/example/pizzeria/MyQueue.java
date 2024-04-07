package org.example.pizzeria;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BlockingQueue собственного производства.
 */
public class MyQueue<T> implements ImyQueue<T> {
    private final Lock lock = new ReentrantLock(); //блокировка.
    private Queue<T> queue = new LinkedList<>(); //очередь.
    private final Condition notFull = lock.newCondition(); //условие неполноты.
    private final Condition notEmpty = lock.newCondition(); //условие непустоты.
    private final int capacity; //вместимость очереди.

    /**
     * если не задан размер, создаём такого размера.
     */
    public MyQueue() {
        this(Integer.MAX_VALUE);
    }

    /**
     * создаём заданного размера.
     */
    public MyQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Getter для вместимости.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * получаем размер очереди.
     */
    @Override
    public int size() {
        lock.lock(); //блокируем.
        try {
            return queue.size(); //возращаем текущий размер.
        } finally {
            lock.unlock(); //разблокируем.
        }
    }

    /**
     * Закидываем в очередь.
     */
    @Override
    public void push(T value) throws InterruptedException {
        lock.lock(); //блокируем
        try {
            while (queue.size() == capacity) {
                notFull.await(); //ждём сигнала о неполноте.
            }
            queue.add(value);  //добавляем в очередь.
            notEmpty.signal(); //отправляем сигнал о непустоте.
        } finally {
            lock.unlock(); //разлокируем.
        }
    }

    /**
     * Достаём из очереди.
     */
    @Override
    public T pop() throws InterruptedException {
        lock.lock(); //блокируем.
        try {
            while (queue.size() == 0) {
                notEmpty.await();  //ждём сигнала о непустоте.
            }
            T value = queue.poll(); //извлекаем элемента.
            notFull.signal(); //отправляем сигнал о неполноте.
            return value; //возвращаем извлечённый элемент.
        } finally {
            lock.unlock(); //разблокируем.
        }
    }

    /**
     * Getter для очереди.
     */
    @Override
    public Queue<T> getQueue() {
        lock.lock(); //блокируем.
        try {
            return queue;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Setter для очереди.
     */
    @Override
    public void setQueue(Queue<T> queue) {
        lock.lock();
        try {
            this.queue = queue;
        } finally {
            lock.unlock(); //разблокируем.
        }
    }
}
