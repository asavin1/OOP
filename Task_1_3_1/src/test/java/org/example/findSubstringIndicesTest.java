package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.example.findSubstringIndices.find;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * тестим.
 */
public class findSubstringIndicesTest {
    /**
     * тестим как было в примере.
     * файл: абракадабра
     * подстрока: бра
     */
    @Test
    void test1() {
        var filename = "src/test/resources/" + "test1.txt";
        var substring = "бра";
        var except = new ArrayList<>(Arrays.asList(1, 8));
        assertEquals(find(filename, substring, false), except);
    }

    /**
     * тестим если у нас расстояние между соседними вхождениями меньше длины подстроки.
     * файл: AAAFAAAAAA
     * подстрока: AAA
     */
    @Test
    void test2() {
        var filename = "src/test/resources/" + "test2.txt";
        var substring = "AAA";
        var except = new ArrayList<>(Arrays.asList(0, 4, 5, 6, 7));
        assertEquals(find(filename, substring, false), except);
    }

    /**
     * тестим если подстроки нет.
     * файл: AAAFAAAAAA
     * подстрока: банан
     */
    @Test
    void test3() {
        var filename = "src/test/resources/" + "test2.txt";
        var substring = "банан";
        var except = new ArrayList<>(List.of());
        assertEquals(find(filename, substring, false), except);
    }

    /**
     * тестим если файл пустой.
     * файл:
     * подстрока: банан
     */
    @Test
    void test4() {
        var filename = "src/test/resources/" + "test3.txt";
        var substring = "банан";
        var except = new ArrayList<>(List.of());
        assertEquals(find(filename, substring, false), except);
    }

    /**
     * тестим вот так.
     * файл: AAAAAAAA
     * подстрока: A
     */
    @Test
    void test5() {
        var filename = "src/test/resources/" + "test4.txt";
        var substring = "A";
        var except = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        assertEquals(find(filename, substring, false), except);
    }

    /**
     * тестим вот так, но читаем из ресурсов.
     * файл: AAAAAAAA
     * подстрока: A
     */
    @Test
    void test6() {
        var filename = "test4.txt";
        var substring = "A";
        var except = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));
        assertEquals(find(filename, substring, true), except);
    }
}
