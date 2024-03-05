package org.example.hasnotprimecheck;

/**
 * Реализация с помощью Thread'ов.
 */
public class ThreadsCheck extends HasNotPrimeCheck {

    int numberOfThreads;

    public ThreadsCheck(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    /**
     * Класс для создании нитей.
     */
    public static class MyThread extends Thread {
        private final int[] list;
        private final boolean[] tasks;
        private final int start;
        private final int end;
        private final int i;

        public MyThread(int[] list, boolean[] tasks, int start, int end, int i) {
            this.list = list;
            this.tasks = tasks;
            this.start = start;
            this.end = end;
            this.i = i;
        }

        @Override
        public void run() {
            try {
                boolean result = false;
                for (int j = start; j < end; j++) {
                    if (!isPrime(list[j])) {
                        result = true;
                        break;
                    }
                    if (Thread.interrupted()) {
                        return;
                    }
                }
                tasks[i] = result;
            } catch (Exception e) {
                System.out.println("Exception caught: " + e.getMessage());
            }
        }
    }

    /**
     * Параллельно с помощью Thread'ов проверяем наличие НЕпростого числа.
     */
    @Override
    boolean hasNotPrime(int[] list) {
        var tasks = new boolean[numberOfThreads]; //результат работы нитей
        var threads = initThreads(numberOfThreads, list, tasks);
        boolean result = false;
        try {
            for (int i = 0; i < numberOfThreads; i++) {
                threads[i].join();
                if (tasks[i]) {
                    result = true;
                    for (int j = 0; j < numberOfThreads; j++) {
                        threads[i].interrupt();
                    }
                    break;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * создание и запуск Thread'ов.
     */
    private Thread[] initThreads(int numberOfThreads, int[] list, boolean[] tasks) {
        int sizeOfBlock = list.length / numberOfThreads;
        int start = 0;
        int end = sizeOfBlock;
        var threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            Thread newThread = new Thread(new MyThread(list, tasks, start, end, i));
            newThread.start();
            threads[i] = newThread;
            start = end;
            if (i == numberOfThreads - 2) {
                end = list.length;
            } else {
                end += sizeOfBlock;
            }
        }
        return threads;
    }


}
