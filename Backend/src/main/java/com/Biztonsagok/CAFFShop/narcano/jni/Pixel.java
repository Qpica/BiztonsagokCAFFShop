package com.Biztonsagok.CAFFShop.narcano.jni;

import java.util.Objects;

public final class Pixel {
    private final int r;
    private final int g;
    private final int b;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int r() {
        return r;
    }

    public int g() {
        return g;
    }

    public int b() {
        return b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Pixel) obj;
        return this.r == that.r &&
                this.g == that.g &&
                this.b == that.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b);
    }

    @Override
    public String toString() {
        return "Pixel[" +
                "r=" + r + ", " +
                "g=" + g + ", " +
                "b=" + b + ']';
    }

}
