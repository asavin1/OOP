package org.example;

import java.util.Arrays;

import org.junit.Test;

public class PolynomialTest {
    @Test
    public void testToString() {
        Polynomial p1 = new Polynomial(new int[]{1, 1, 1, 1});
        assert (p1.toString().equals("x^3 + x^2 + x + 1"));
    }

    @Test
    public void testGetCoefs() {
        Polynomial p1 = new Polynomial(new int[]{1, 2, 3});
        assert (Arrays.equals(p1.getCoefs(), new int[]{1, 2, 3}));
    }

    @Test
    public void testToStringEmptyArray() {
        Polynomial p1 = new Polynomial(new int[]{});
        assert (p1.toString().equals("0"));
    }

    @Test
    public void testToStringZeroArray() {
        Polynomial p1 = new Polynomial(new int[]{0, 0, 0});
        assert (p1.toString().equals("0"));
    }

    @Test
    public void testToStringOneNoZeroElement() {
        Polynomial p1 = new Polynomial(new int[]{0, -1, 0});
        assert (p1.toString().equals("-x"));
    }

    @Test
    public void testPlus() {
        Polynomial p1 = new Polynomial(new int[]{2, 2, 2});
        Polynomial p2 = new Polynomial(new int[]{1, 1, 1});
        Polynomial p3 = new Polynomial(new int[]{3, 3, 3});
        assert (p1.plus(p2).equals(p3));
    }

    @Test
    public void testMinus() {
        Polynomial p1 = new Polynomial(new int[]{5, 4, 3});
        Polynomial p2 = new Polynomial(new int[]{3, 2, 1});
        Polynomial p3 = new Polynomial(new int[]{2, 2, 2});
        assert (p1.minus(p2).equals(p3));
    }

    @Test
    public void testTimes() {
        Polynomial p1 = new Polynomial(new int[]{2, 3, 4});
        Polynomial p2 = new Polynomial(new int[]{2, 5});
        Polynomial p3 = new Polynomial(new int[]{4, 16, 23, 20});
        assert (p1.times(p2).equals(p3));
    }

    @Test
    public void testEvaluate() {
        Polynomial p1 = new Polynomial(new int[]{3, 4, 5});
        assert (p1.evaluate(2) == 31);
    }

    @Test
    public void testDifferentiate() {
        Polynomial p1 = new Polynomial(new int[]{5, 4, 3});
        Polynomial p2 = new Polynomial(new int[]{4, 6});
        assert (p1.differentiate(1).equals(p2));
    }

    @Test
    public void testEquals() {
        Polynomial p1 = new Polynomial(new int[]{5, 4, 3});
        Polynomial p2 = new Polynomial(new int[]{5, 4, 3});
        assert (p1.equals(p2));
    }
}
