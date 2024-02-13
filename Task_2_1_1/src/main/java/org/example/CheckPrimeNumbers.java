package org.example;

import java.util.Arrays;

/**
 * Класс для проверки есть ли в массиве НЕпростое число тремя способами.
 */
public class CheckPrimeNumbers {
    /**
     * Проверка на простоту.
     */
    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        } else {
            for (int i = 2; i * i <= number; i++) {
                if (number % i == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Последовательно делаем.
     */
    public static boolean consistentCheck(int[] list) {
        for (int i : list) {
            if (!isPrime(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Делаем параллельно с помощью нитей.
     */
    public static boolean threadsCheck(int[] list, int numberOfThreads) {
        int sizeOfBlock = list.length / numberOfThreads;
        int start = 0;
        int end = sizeOfBlock;
        var threads = new Thread[numberOfThreads];
        var tasks = new boolean[numberOfThreads]; //результат работы нитей

        for (int i = 0; i < numberOfThreads; i++) {
            final int finalStart = start;
            final int finalEnd = end;
            int finalI = i;

            Thread newThread = new Thread(() -> {
                boolean result = false;
                for (int j = finalStart; j < finalEnd; j++) {
                    if (!isPrime(list[j])) {
                        result = true;
                        break;
                    }
                }
                synchronized (tasks) {
                    tasks[finalI] = result;
                }
            });
            newThread.start();
            threads[i] = newThread;
            start = end;
            if (i == numberOfThreads - 2) {
                end = list.length;
            } else {
                end += sizeOfBlock;
            }
        }
        boolean result = false;
        try {
            for (int i = 0; i < numberOfThreads; i++) {
                threads[i].join();
                if (tasks[i]) {
                    result = true;
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * parallelStream().
     */
    public static boolean parallelCheck(int[] list) {
        return !Arrays.stream(list)
                .parallel()
                .allMatch(CheckPrimeNumbers::isPrime);
    }
}
