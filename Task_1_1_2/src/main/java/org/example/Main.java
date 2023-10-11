package org.example;

public class Main {
    public static void main(String[] args) {
        var p1 = new Polynomial(new int[] {4, 3, 6, 7});
        System.out.println(p1.evaluate(1));
    }
}