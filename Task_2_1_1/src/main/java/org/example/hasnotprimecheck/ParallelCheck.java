package org.example.hasnotprimecheck;

import java.util.Arrays;

public class ParallelCheck extends HasNotPrimeCheck {

    int numberOfThreads;

    public ParallelCheck(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    boolean hasNotPrime(int[] list) {
        //выставляем кол-во используемых потоков
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism",
                String.valueOf(numberOfThreads));
        boolean result = Arrays.stream(list).parallel().allMatch(HasNotPrimeCheck::isPrime);
        //перед возвращением выставляем назад значение
        System.clearProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
        return !result;
    }
}
