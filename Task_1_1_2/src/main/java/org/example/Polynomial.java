package org.example;

import java.util.Arrays;

public class Polynomial {
    private int[] coefs;

    public Polynomial(int[] arr) {
        coefs = new int[arr.length];
        System.arraycopy(arr, 0, coefs, 0, arr.length);
    }

    public int[] getCoefs() {
        return coefs;
    }

    @Override
    public String toString() {
        var str = new StringBuilder();

        if (this.coefs.length == 1 && this.coefs[0] != 0) {
            str.append(this.coefs[0]);
            return str.toString();
        }

        if (this.coefs.length == 2) {
            if (this.coefs[0] != 0 && this.coefs[0] != 1) {
                str.append(this.coefs[0]).append("x");
            } else if (this.coefs[0] == 1) {
                str.append("x");
            }
            if (this.coefs[1] != 0) {
                str.append(sign(this.coefs[1])).append(Math.abs(this.coefs[1]));
            }
            return str.toString();
        }

        if (this.coefs.length > 2) {
            if (this.coefs[0] != 0 && this.coefs[0] != 1) {
                str.append(this.coefs[0]).append("x^").append(this.coefs.length - 1);
            } else if (this.coefs[0] == 1) {
                str.append("x^").append(this.coefs.length - 1);
            }

            for (int i = 1; i < this.coefs.length - 2; i++) {
                if (this.coefs[i] != 0 && this.coefs[0] != 1) {
                    str.append(sign(this.coefs[i])).append(Math.abs(this.coefs[i]));
                    str.append("x^").append(this.coefs.length - i - 1);
                } else if (this.coefs[0] == 1) {
                    str.append(sign(this.coefs[i])).append("x^").append(this.coefs.length - i - 1);
                }
            }

            if (this.coefs[this.coefs.length - 2] != 0 && this.coefs[this.coefs.length - 2] != 1) {
                str.append(sign(this.coefs[this.coefs.length - 2]));
                str.append(Math.abs(this.coefs[this.coefs.length - 2])).append("x");
            } else if (this.coefs[this.coefs.length - 2] == 1) {
                str.append(sign(this.coefs[this.coefs.length - 2])).append("x");
            }

            if (this.coefs[this.coefs.length - 1] != 0) {
                str.append(sign(this.coefs[this.coefs.length - 1]));
                str.append(Math.abs(this.coefs[this.coefs.length - 1]));
            }
        }

        if (str.toString().isEmpty()) {
            return "0";
        }
        return str.toString();
    }

    private static String sign(int a) {
        if (a < 0) {
            return " - ";
        } else {
            return " + ";
        }
    }

    /**
     * сложение.
     */
    public Polynomial plus(Polynomial p) {
        int dif = Math.abs(this.coefs.length - p.coefs.length);
        if (this.coefs.length > p.coefs.length) {
            for (int i = p.coefs.length - 1; i >= 0; i--) {
                this.coefs[i + dif] += p.coefs[i];
            }
            return this;
        } else {
            for (int i = this.coefs.length - 1; i >= 0; i--) {
                p.coefs[i + dif] += this.coefs[i];
            }
            return p;
        }
    }

    /**
     * вычитание.
     */
    public Polynomial minus(Polynomial p) {
        int dif = Math.abs(this.coefs.length - p.coefs.length);
        if (this.coefs.length >= p.coefs.length) {
            for (int i = p.coefs.length - 1; i >= 0; i--) {
                this.coefs[i + dif] -= p.coefs[i];
            }
            return this;
        } else {
            for (int i = this.coefs.length - 1; i >= 0; i--) {
                p.coefs[i + dif] -= this.coefs[i];
            }
            return p;
        }
    }

    /**
     * умножение.
     */
    public Polynomial times(Polynomial p) {
        if ((this.coefs.length != 0) && (p.coefs.length != 0)) {
            var newp = new Polynomial(new int[this.coefs.length + p.coefs.length - 1]);

            for (int i = 0; i < this.coefs.length; i++) {
                for (int j = 0; j < p.coefs.length; j++) {
                    newp.coefs[i + j] += p.coefs[j] * this.coefs[i];
                }
            }
            return newp;
        } else {
            System.out.print("Не удалось выполнить операцию");
            return this;
        }
    }

    /**
     * значение в точке.
     */
    public int evaluate(int x) {
        int eval = 0;
        for (int i = 0; i < this.coefs.length; i++) {
            eval += this.coefs[i] * (int) Math.pow(x, (this.coefs.length - i - 1));
        }
        return eval;
    }

    /**
     * i-ая производная.
     */
    public void differentiate(int p) {
        if (this.coefs.length < p) {
            System.out.print("Не удалось выполнить операцию");
        }
        if ((this.coefs.length - p) > 1) {
            for (int k = 0; k < p; k++) {
                var newp = new Polynomial(new int[this.coefs.length - 1]);
                for (int i = 0; i < this.coefs.length - 1; i++) {
                    newp.coefs[i] = (this.coefs.length - i - 1) * this.coefs[i];
                }
                this.coefs = newp.coefs;
            }
        }
        if ((this.coefs.length == 1) || this.coefs.length == p) {
            var newp = new Polynomial(new int[1]);
            newp.coefs[0] = 0;
            this.coefs = newp.coefs;

        }
    }

    @Override
    public boolean equals(Object p) {
        if (p.getClass() == this.getClass()) {
            return (Arrays.equals(((Polynomial) p).coefs, this.coefs));
        }
        return false;
    }
}
