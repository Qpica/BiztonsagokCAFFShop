package com.narcano.jni;

import java.util.Objects;

public final class SimpleDate {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public SimpleDate(int year, int month, int day, int hour, int minute) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int year() {
        return year;
    }

    public int month() {
        return month;
    }

    public int day() {
        return day;
    }

    public int hour() {
        return hour;
    }

    public int minute() {
        return minute;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SimpleDate) obj;
        return this.year == that.year &&
                this.month == that.month &&
                this.day == that.day &&
                this.hour == that.hour &&
                this.minute == that.minute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, day, hour, minute);
    }

    @Override
    public String toString() {
        return "SimpleDate[" +
                "year=" + year + ", " +
                "month=" + month + ", " +
                "day=" + day + ", " +
                "hour=" + hour + ", " +
                "minute=" + minute + ']';
    }
}
