package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.example.findSubstringIndices.find;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class findSubstringIndicesTest {
    @Test
    void test1() {
        var filename = "src/test/resources/" + "test1.txt";
        var substring = "бра";
        var except = new ArrayList<>(Arrays.asList(1, 8));
        assertEquals(find(filename, substring), except);
    }

    @Test
    void test2() {
        var filename = "src/test/resources/" + "test2.txt";
        var substring = "AAA";
        var except = new ArrayList<>(Arrays.asList(0, 4, 5, 6, 7));
        assertEquals(find(filename, substring), except);
    }
}
