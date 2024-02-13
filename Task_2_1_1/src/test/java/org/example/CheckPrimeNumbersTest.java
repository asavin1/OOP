package org.example;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;

import static org.example.CheckPrimeNumbers.consistentCheck;
import static org.example.CheckPrimeNumbers.parallelCheck;
import static org.example.CheckPrimeNumbers.threadsCheck;


/**
 * тестим
 */
public class CheckPrimeNumbersTest {
    /**
     * Время последовательного исполнения.
     */
    public long testConsistentCheckTime() {
        int[] array = new int[100000];
        Arrays.fill(array, 20319251);

        long start = System.nanoTime();
        consistentCheck(array);
        long end = System.nanoTime();

        return Duration.ofNanos(end - start).toMillis();
    }

    /**
     * Время параллельного исполнения через StreamParallel.
     */
    public long testParallelCheckTime() {
        int[] array = new int[100000];
        Arrays.fill(array, 20319251);

        long start = System.nanoTime();
        parallelCheck(array);
        long end = System.nanoTime();

        return Duration.ofNanos(end - start).toMillis();
    }

    /**
     * Время параллельного исполнения через Threads.
     */
    public long testThreadsCheckTime(int a) {
        int[] array = new int[100000];
        Arrays.fill(array, 20319251);

        long start = System.nanoTime();
        threadsCheck(array, a);
        long end = System.nanoTime();

        return Duration.ofNanos(end - start).toMillis();
    }

    /**
     * сравниваем время.
     */
    @Test
    public void compareTime() {
        long par = testParallelCheckTime();
        long con = testConsistentCheckTime();
        System.out.println("consistent: " + con + '\n');
        for (int i = 1; i <= 6; i++) {
            System.out.println("thread " + i + ": " + testThreadsCheckTime(i) + '\n');
        }
        System.out.println("parallel: " + par + '\n');
    }
}
