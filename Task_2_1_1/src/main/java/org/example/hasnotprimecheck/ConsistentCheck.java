package org.example.hasnotprimecheck;

/**
 * Последовательно делаем.
 */
public class ConsistentCheck extends HasNotPrimeCheck {
    /**
     * Последовательно проверяем наличие НЕпростого числа.
     */
    @Override
    boolean hasNotPrime(int[] list) {
        for (int i : list) {
            if (!isPrime(i)) {
                return true;
            }
        }
        return false;
    }
}
