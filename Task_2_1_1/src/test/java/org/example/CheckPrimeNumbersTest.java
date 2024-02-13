package org.example;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;

import static org.example.checkPrimeNumbers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckPrimeNumbersTest {
    public long testConsistentCheckTime() {
        int[] array = new int[100000];
        Arrays.fill(array, 20319251);

        long start = System.nanoTime();
        boolean result = consistentCheck(array);
        long end = System.nanoTime();

        assertFalse(result);

        return Duration.ofNanos(end - start).toMillis();
    }

    public long testParallelCheckTime() {
        int[] array = new int[100000];
        Arrays.fill(array, 20319251);

        long start = System.nanoTime();
        boolean result = parallelCheck(array);
        long end = System.nanoTime();

        assertFalse(result);

        return Duration.ofNanos(end - start).toMillis();
    }

    public long testThreadsCheckTime(int a) {
        int[] array = new int[100000];
        Arrays.fill(array, 20319251);

        long start = System.nanoTime();
        boolean result = threadsCheck(array, a);
        long end = System.nanoTime();

        assertFalse(result);

        return Duration.ofNanos(end - start).toMillis();
    }

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
