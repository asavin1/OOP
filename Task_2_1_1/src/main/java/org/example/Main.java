package org.example;

import static org.example.checkPrimeNumbers.*;

public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 2, 2, 2, 2, 1};
        System.out.print( threadsCheck(a, 6));
        System.out.print( consistentCheck(a));
        System.out.print( parallelCheck(a));
    }
}