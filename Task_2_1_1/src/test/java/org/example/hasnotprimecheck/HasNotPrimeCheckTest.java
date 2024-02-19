package org.example.hasnotprimecheck;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * тестим.
 */
public class HasNotPrimeCheckTest {
    /**
     * Тестируем параллельно с помощью parallelStream.
     * берём минимальное и максимальное значение int.
     */
    @Test
    public void parallelTestBoundaryValues() {
        var list = new int[2];
        list[0] = Integer.MIN_VALUE;
        list[1] = Integer.MAX_VALUE;
        var a = new ParallelCheck(6);
        assertTrue(a.hasNotPrime(list));
    }

    /**
     * Тестируем параллельно с помощью threads.
     * берём минимальное и максимальное значение int.
     */
    @Test
    public void threadsTestBoundaryValues() {
        var list = new int[2];
        list[0] = Integer.MIN_VALUE;
        list[1] = Integer.MAX_VALUE;
        var a = new ThreadsCheck(6);
        assertTrue(a.hasNotPrime(list));
    }

    /**
     * Тестируем последовательно.
     * берём минимальное и максимальное значение int.
     */
    @Test
    public void consistentTestBoundaryValues() {
        var list = new int[2];
        list[0] = Integer.MIN_VALUE;
        list[1] = Integer.MAX_VALUE;
        var a = new ConsistentCheck();
        assertTrue(a.hasNotPrime(list));
    }

    /**
     * Тестируем параллельно с помощью parallelStream.
     * даём список без НЕпростых чисел.
     */
    @Test
    public void parallelTestFalse() {
        var list = new int[]{2, 2};
        var a = new ParallelCheck(6);
        assertFalse(a.hasNotPrime(list));
    }

    /**
     * Тестируем параллельно с помощью threads.
     * даём список без НЕпростых чисел.
     */
    @Test
    public void threadsTestFalse() {
        var list = new int[]{2, 2};
        var a = new ThreadsCheck(6);
        assertFalse(a.hasNotPrime(list));
    }

    /**
     * Тестируем последовательно.
     * даём список без НЕпростых чисел.
     */
    @Test
    public void consistentTestFalse() {
        var list = new int[]{2, 2};
        var a = new ConsistentCheck();
        assertFalse(a.hasNotPrime(list));
    }

    /**
     * Тестируем параллельно с помощью parallelStream.
     * даём список список из 100000 элементов.
     */
    @Test
    public void parallelTestBigData() {
        int[] list = new int[100000];
        Arrays.fill(list, 20319251);
        var a = new ParallelCheck(6);
        assertFalse(a.hasNotPrime(list));
    }

    /**
     * Тестируем параллельно с помощью threads.
     * даём список список из 100000 элементов.
     */
    @Test
    public void threadsTestBigData() {
        int[] list = new int[100000];
        Arrays.fill(list, 20319251);
        var a = new ThreadsCheck(6);
        assertFalse(a.hasNotPrime(list));
    }

    /**
     * Тестируем последовательно.
     * даём список список из 100000 элементов.
     */
    @Test
    public void consistentTestBigData() {
        int[] list = new int[100000];
        Arrays.fill(list, 20319251);
        var a = new ConsistentCheck();
        assertFalse(a.hasNotPrime(list));
    }
}
