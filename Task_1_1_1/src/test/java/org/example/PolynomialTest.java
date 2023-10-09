package org.example;

import org.junit.jupiter.api.Test;

public class PolynomialTest {
    @Test
    void test1()  {
        Polynomial p1 = new Polynomial(new int [] {1, 1, 1, 1});
        assert(p1.toString().equals("x^3 + x^2 + x + 1"));
    }
    @Test
    void test2()  {
        Polynomial p1 = new Polynomial(new int [] {});
        assert(p1.toString().equals("0"));
    }
    @Test
    void test3()  {
        Polynomial p1 = new Polynomial(new int [] {0, 0, 0});
        assert(p1.toString().equals("0"));
    }
    @Test
    void test4()  {
        Polynomial p1 = new Polynomial(new int [] {2, 2, 2});
        Polynomial p2 = new Polynomial(new int [] {1, 1, 1});
        Polynomial p3 = new Polynomial(new int [] {3, 3, 3});
        assert(p1.plus(p2).equals(p3));
    }
    @Test
    void test5()  {
        Polynomial p1 = new Polynomial(new int [] {5, 4, 3});
        Polynomial p2 = new Polynomial(new int [] {3, 2, 1});
        Polynomial p3 = new Polynomial(new int [] {2, 2, 2});
        assert(p1.minus(p2).equals(p3));
    }
    @Test
    void test6()  {
        Polynomial p1 = new Polynomial(new int [] {2, 3, 4});
        Polynomial p2 = new Polynomial(new int [] {2, 5});
        Polynomial p3 = new Polynomial(new int [] {4, 16, 23, 20});
        assert(p1.times(p2).equals(p3));
    }
    @Test
    void test7()  {
        Polynomial p1 = new Polynomial(new int [] {5, 4, 3});
        assert(p1.evaluate(2) == 31);
    }
    @Test
    void test8()  {
        Polynomial p1 = new Polynomial(new int [] {5, 4, 3});
        Polynomial p2 = new Polynomial(new int [] {10, 4});
        assert(p1.differentiate(1).equals(p2));
    }
    @Test
    void test9()  {
        Polynomial p1 = new Polynomial(new int [] {5, 4, 3});
        Polynomial p2 = new Polynomial(new int [] {5, 4, 3});
        assert(p1.equals(p2));
    }
}
