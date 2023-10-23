package org.example;

public class Main {
    public static void main(String[] args) {
        var p1 = new Polynomial(new int[] {1, 0, 0});
        var p2 = new Polynomial(new int[] {0});
        p1.differentiate(5);
        System.out.println(p1);
    }
}