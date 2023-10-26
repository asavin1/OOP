package org.example;

import java.util.Arrays;

public class Polynomial {
    private int[] coefs;

    public Polynomial(int[] arr) {
        coefs = new int[arr.length];
        System.arraycopy(arr, 0, coefs, 0, arr.length);
    }

    public int[] getCoefs() {
        int[] c;
        c = new int[this.coefs.length];
        System.arraycopy(this.coefs, 0, c, 0, this.coefs.length);
        return c;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        boolean isFirstNonZero = true; // Флаг для определения первого ненулевого элемента

        for (int i = coefs.length - 1; i >= 0; i--) {
            if (coefs[i] != 0) {
                if (!isFirstNonZero) {
                    if (coefs[i] > 0) {
                        sb.append(" + ");
                    } else {
                        sb.append(" - ");
                    }
                } else {
                    if (coefs[i] < 0) {
                        sb.append("-");
                    }
                    isFirstNonZero = false;
                }

                if (Math.abs(coefs[i]) != 1 || i == 0) {
                    sb.append(Math.abs(coefs[i]));
                }

                if (i > 0) {
                    sb.append("x");

                    if (i > 1) {
                        sb.append("^").append(i);
                    }
                }
            }
        }

        if (sb.isEmpty()) { // Если полином состоит только из нулевых коэффициентов
            sb.append("0");
        }

        return sb.toString();
    }

    /**
     * сложение.
     */
    public Polynomial plus(Polynomial p) {
        int maxLength = Math.max(coefs.length, p.coefs.length);
        int[] resultCoefs = new int[maxLength];

        for (int i = 0; i < maxLength; i++) {
            int coef1;
            if (i < coefs.length) {
                coef1 = coefs[i];
            } else {
                coef1 = 0;
            }

            int coef2;
            if (i < p.coefs.length) {
                coef2 = p.coefs[i];
            } else {
                coef2 = 0;
            }

            resultCoefs[i] = coef1 + coef2;
        }

        return new Polynomial(resultCoefs);
    }

    /**
     * вычитание.
     */
    public Polynomial minus(Polynomial p) {
        int maxLength = Math.max(coefs.length, p.coefs.length);
        int[] resultCoefs = new int[maxLength];

        for (int i = 0; i < maxLength; i++) {
            int coef1;
            if (i < coefs.length) {
                coef1 = coefs[i];
            } else {
                coef1 = 0;
            }

            int coef2;
            if (i < p.coefs.length) {
                coef2 = p.coefs[i];
            } else {
                coef2 = 0;
            }

            resultCoefs[i] = coef1 - coef2;
        }

        return new Polynomial(resultCoefs);
    }

    /**
     * умножение.
     */
    public Polynomial times(Polynomial p) {
        if (coefs.length == 0 || p.coefs.length == 0) {
            return new Polynomial(new int[0]);
        }

        int[] resultCoefs = new int[coefs.length + p.coefs.length - 1];

        for (int i = 0; i < coefs.length; i++) {
            for (int j = 0; j < p.coefs.length; j++) {
                resultCoefs[i + j] += coefs[i] * p.coefs[j];
            }
        }

        return new Polynomial(resultCoefs);
    }

    /**
     * значение в точке.
     */
    public int evaluate(int x) {
        int eval = 0;
        int power = 1;

        for (int i = 0; i < coefs.length; i++) {
            eval += coefs[i] * power;
            power *= x;
        }

        return eval;
    }

    /**
     * i-ая производная.
     */
    public Polynomial differentiate(int p) {
        int[] tempCoefs = new int[this.coefs.length];
        System.arraycopy(this.coefs, 0, tempCoefs, 0, this.coefs.length);
        var temp = new Polynomial(tempCoefs);
        if ((this.coefs.length - p) > 1) {
            for (int k = 0; k < p; k++) {
                var newp = new Polynomial(new int[temp.coefs.length - 1]);
                for (int i = 0; i < temp.coefs.length - 1; i++) {
                    newp.coefs[i] = (i+1) * temp.coefs[i+1];
                }
                temp.coefs = newp.coefs;
            }
            return temp;
        }
        else {
            var newp = new Polynomial(new int[1]);
            newp.coefs[0] = 0;
            return newp;
        }
    }


    @Override
    public boolean equals(Object p) {
        if (p == null){
            System.out.print("Вы передали null\n");
            return false;
        }
        if (p.getClass() == this.getClass()) {
            return Arrays.equals(((Polynomial) p).coefs, this.coefs);
        }
        return false;
    }
}
