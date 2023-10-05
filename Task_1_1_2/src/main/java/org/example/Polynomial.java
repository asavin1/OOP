package org.example;

public class Polynomial {
    int[] coefs;
    Polynomial (int[] arr){
        coefs = new int[arr.length];
        System.arraycopy(arr, 0, coefs, 0, arr.length);
    }

    @Override
    public String toString(){
        String str = "";
        if (this.coefs[0] != 0) {
            str = this.coefs[0] + "x^" + (this.coefs.length-1);
        }
        for (int i = 1; i < this.coefs.length-2; i++){
            if (this.coefs[i] != 0) {
                str += sign(this.coefs[i]) + Math.abs(this.coefs[i]) + "x^" + (this.coefs.length-i-1);
            }
        }
        if (this.coefs[this.coefs.length-2] != 0) {
            str += sign(this.coefs[this.coefs.length-2]) + Math.abs(this.coefs[this.coefs.length-2]) + "x";
        }
        if (this.coefs[this.coefs.length-1] != 0) {
            str += sign(this.coefs[this.coefs.length-1]) + Math.abs(this.coefs[this.coefs.length-1]);
        }
        return str;
    }

    static String sign(int a) {
        if (a < 0) {
            return " - ";
        }
        else {
            return " + ";
        }
    }

    public Polynomial plus(Polynomial p) {
        int dif = Math.abs(this.coefs.length - p.coefs.length);
        if (this.coefs.length > p.coefs.length) {
            for (int i = p.coefs.length-1; i >= 0; i--) {
                this.coefs[i+dif] += p.coefs[i];
            }
            return this;
        }
        else {
            for (int i = this.coefs.length-1; i >= 0; i--) {
                p.coefs[i+dif] += this.coefs[i];
            }
            return p;
        }
    }

    public Polynomial minus(Polynomial p) {
        int dif = Math.abs(this.coefs.length - p.coefs.length);
        if (this.coefs.length > p.coefs.length) {
            for (int i = p.coefs.length-1; i >= 0; i--) {
                this.coefs[i+dif] -= p.coefs[i];
            }
            return this;
        }
        else {
            for (int i = this.coefs.length-1; i >= 0; i--) {
                p.coefs[i+dif] -= this.coefs[i];
            }
            return p;
        }
    }

    public Polynomial times(Polynomial p) {
        Polynomial newp = new Polynomial(new int[this.coefs.length + p.coefs.length - 1]);

        for (int i = 0; i < this.coefs.length; i++) {
            for (int j = 0; j < p.coefs.length; j++) {
                newp.coefs[i+j] += p.coefs[j] * this.coefs[i];
            }
        }
        return newp;
    }

    public int evaluate(int x) {
        int eval = 0;
        for (int i=0; i < this.coefs.length; i++) {
            eval += this.coefs[i] * Math.pow(x, (this.coefs.length-i-1));
        }
        return eval;
    }
}
