package com.narcano.jni;

import java.util.Arrays;

public final class CAFF {
    private final SimpleDate creationDate;
    private final String creator;
    private final Frame[] content;

    public CAFF(SimpleDate creationDate, String creator, Frame[] content) {
        this.creationDate = creationDate;
        this.creator = creator;
        this.content = content;
    }

    public SimpleDate creationDate() {
        return creationDate;
    }

    public String creator() {
        return creator;
    }

    public Frame[] content() {
        return content;
    }

    public int[] getPreview() {
        if (content.length > 0) {
            return content[0].ciff().pixels();
        }
        return null;
    }

    @Override
    public String toString() {
        return "CAFF{" +
                "creationDate=" + creationDate +
                ", creator='" + creator + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
