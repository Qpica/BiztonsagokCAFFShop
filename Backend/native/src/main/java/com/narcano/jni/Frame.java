package com.narcano.jni;

import java.util.Objects;

public final class Frame {
    private final int ms;
    private final CIFF ciff;

    public Frame(int ms, CIFF ciff) {
        this.ms = ms;
        this.ciff = ciff;
    }

    public int ms() {
        return ms;
    }

    public CIFF ciff() {
        return ciff;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Frame) obj;
        return this.ms == that.ms &&
                Objects.equals(this.ciff, that.ciff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ms, ciff);
    }

    @Override
    public String toString() {
        return "Frame[" +
                "ms=" + ms + ", " +
                "ciff=" + ciff + ']';
    }
}