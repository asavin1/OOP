package org.example.hasnotprimecheck;

/**
 * Абстрактный класс для нескольких реализаций.
 */
public abstract class HasNotPrimeCheck {
    /**
     * Абстрактный метод проверки на наличие НЕпростого числа.
     */
    abstract boolean hasNotPrime(int[] list);

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
}
