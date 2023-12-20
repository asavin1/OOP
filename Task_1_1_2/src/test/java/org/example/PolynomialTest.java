package org.example;

import java.util.Arrays;

import org.junit.jupiter.api.Test;


public class PolynomialTest {
    @Test
    void testToString() {
        Polynomial p1 = new Polynomial(new int[]{1, 1, 1, 1});
        assert (p1.toString().equals("x^3 + x^2 + x + 1"));
    }

    @Test
    void testGetCoefs() {
        Polynomial p1 = new Polynomial(new int[]{1, 2, 3});
        assert (Arrays.equals(p1.getCoefs(), new int[]{1, 2, 3}));
    }

    @Test
    void testToStringEmptyArray() {
        Polynomial p1 = new Polynomial(new int[]{});
        assert (p1.toString().equals("0"));
    }

    @Test
    void testToStringZeroArray() {
        Polynomial p1 = new Polynomial(new int[]{0, 0, 0});
        assert (p1.toString().equals("0"));
    }

    @Test
    void testToStringOneNoZeroElement() {
        Polynomial p1 = new Polynomial(new int[]{0, -1, 0});
        assert (p1.toString().equals("-x"));
    }

    @Test
    void testPlus() {
        Polynomial p1 = new Polynomial(new int[]{2, 2, 2});
        Polynomial p2 = new Polynomial(new int[]{1, 1, 1});
        Polynomial p3 = new Polynomial(new int[]{3, 3, 3});
        assert (p1.plus(p2).equals(p3));
    }

    @Test
    void testMinus() {
        Polynomial p1 = new Polynomial(new int[]{5, 4, 3});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 1});
        Polynomial p3 = new Polynomial(new int[]{2, 2, 2});
        assert (p1.minus(p2).equals(p3));
    }

    @Test
    void testTimes() {
        Polynomial p1 = new Polynomial(new int[]{2, 3, 4});
        Polynomial p2 = new Polynomial(new int[]{2, 5});
        Polynomial p3 = new Polynomial(new int[]{4, 16, 23, 20});
        assert (p1.times(p2).equals(p3));
    }

    @Test
    void testEvaluate() {
        Polynomial p1 = new Polynomial(new int[]{3, 4, 5});
        assert (p1.evaluate(2) == 31);
    }

    @Test
    void testDifferentiate() {
        Polynomial p1 = new Polynomial(new int[]{5, 4, 3});
        Polynomial p2 = new Polynomial(new int[]{4, 6});
        assert (p1.differentiate(1).equals(p2));
    }

    @Test
    void testEquals() {
        Polynomial p1 = new Polynomial(new int[]{5, 4, 3});
        Polynomial p2 = new Polynomial(new int[]{5, 4, 3});
        assert (p1.equals(p2));
    }
}
