package com.narcano.jni;


import java.util.Arrays;

public final class CIFF {
    private final int height;
    private final int width;
    private final String caption;
    private final String[] tags;
    private final int[] pixels;

    public CIFF(int height, int width, String caption, String[] tags, int[] pixels) {
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

    public String[] tags() {
        return tags;
    }

    public int[] pixels() {
        return pixels;
    }

    @Override
    public String toString() {
        return "CIFF{" +
                "height=" + height +
                ", width=" + width +
                ", caption='" + caption + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", pixels=" + Arrays.toString(pixels) +
                '}';
    }
}
