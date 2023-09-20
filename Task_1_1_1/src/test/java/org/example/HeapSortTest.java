package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HeapSortTest {
    @Test
    void checkMain()  {
        int[] arr = {4, 10, 3, 5, 1};
        int[] ans = {1, 3, 4, 5, 10};

        HeapSort ob = new HeapSort();
        ob.sort(arr);

        assertArrayEquals(ans, arr);
    }


    @Test
    void doINeedToCheckDefaultConstructor() {
        final var sampleInstance = new HeapSort();
    }
}
