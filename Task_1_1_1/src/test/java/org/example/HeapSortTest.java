package org.example;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class HeapSortTest {
    @Test
    void test1()  {
        int[] arr = {4, 10, 3, 5, 1};
        int[] ans = {1, 3, 4, 5, 10};
        HeapSort.sort(arr);
        assertArrayEquals(ans, arr);
    }

    @Test
    void test2()  {
        int[] array = new int[100000];
        for (int i = 0; i < array.length; i++) {
            array[i] = ((int)(Math.random() * 100000));
        }
        int[] ans = Arrays.copyOf(array, array.length);
        HeapSort.sort(array);
        Arrays.sort(ans);
        assertArrayEquals(ans, array);
    }

    @Test
    void test3()  {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = ((int)(Math.random() * -100));
        }
        int[] ans = Arrays.copyOf(array, array.length);
        HeapSort.sort(array);
        Arrays.sort(ans);
        assertArrayEquals(ans, array);
    }

    @Test
    void test4()  {
        int[] array = {1};
        int[] ans = {1};
        HeapSort.sort(array);
        assertArrayEquals(ans, array);
    }

    @Test
    void test5()  {
        int[] array = {};
        int[] ans = {};
        HeapSort.sort(array);
        assertArrayEquals(ans, array);
    }
}
