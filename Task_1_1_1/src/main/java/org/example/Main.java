package org.example;

import java.util.Scanner;

/**
 * Точка входа
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Insert size array:");
        int size = input.nextInt();
        int[] array = new int[size];
        System.out.println("Insert array elements:");

        for (int i = 0; i < size; i++) {
            array[i] = input.nextInt(); //
        }
        HeapSort.sort(array);
        System.out.println("Sorted array is");
        for (int i = 0; i < size; ++i) {
            System.out.print(array[i] + " ");
        }
    }
}
