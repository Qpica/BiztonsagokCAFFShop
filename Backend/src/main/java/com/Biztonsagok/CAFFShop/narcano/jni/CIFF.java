package com.Biztonsagok.CAFFShop.narcano.jni;


import java.util.List;
import java.util.Objects;

public final class CIFF {
    private final int height;
    private final int width;
    private final String caption;
    private final List<String> tags;
    private final List<Pixel> pixels;

    public CIFF(int height, int width, String caption, List<String> tags, List<Pixel> pixels) {
        this.height = height;
        this.width = width;
        this.caption = caption;
        this.tags = tags;
        this.pixels = pixels;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    public String caption() {
        return caption;
    }

    public List<String> tags() {
        return tags;
    }

    public List<Pixel> pixels() {
        return pixels;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CIFF) obj;
        return this.height == that.height &&
                this.width == that.width &&
                Objects.equals(this.caption, that.caption) &&
                Objects.equals(this.tags, that.tags) &&
                Objects.equals(this.pixels, that.pixels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, caption, tags, pixels);
    }

    @Override
    public String toString() {
        return "CIFF[" +
                "height=" + height + ", " +
                "width=" + width + ", " +
                "caption=" + caption + ", " +
                "tags=" + tags + ", " +
                "pixels=" + pixels + ']';
    }
}
