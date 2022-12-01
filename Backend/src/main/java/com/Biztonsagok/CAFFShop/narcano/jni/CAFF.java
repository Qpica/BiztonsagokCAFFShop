package com.Biztonsagok.CAFFShop.narcano.jni;

import java.util.List;
import java.util.Objects;

public final class CAFF {
    private final SimpleDate creationDate;
    private final String creator;
    private final List<Pair<Integer, CIFF>> content;

    public CAFF(SimpleDate creationDate, String creator, List<Pair<Integer, CIFF>> content) {
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

    public List<Pair<Integer, CIFF>> content() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CAFF) obj;
        return Objects.equals(this.creationDate, that.creationDate) &&
                Objects.equals(this.creator, that.creator) &&
                Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate, creator, content);
    }

    @Override
    public String toString() {
        return "CAFF[" +
                "creationDate=" + creationDate + ", " +
                "creator=" + creator + ", " +
                "content=" + content + ']';
    }

}
