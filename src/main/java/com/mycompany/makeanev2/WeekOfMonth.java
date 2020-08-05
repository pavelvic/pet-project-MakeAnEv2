package com.mycompany.makeanev2;

import java.time.LocalDate;
import java.time.Month;

public class WeekOfMonth {

    private final LocalDate mon;
    private final LocalDate tue;
    private final LocalDate wed;
    private final LocalDate thu;
    private final LocalDate fri;
    private final LocalDate sat;
    private final LocalDate sun;
    private final int year;
    private final Month month;

    public WeekOfMonth(LocalDate mon, LocalDate tue, LocalDate wed, LocalDate thu, LocalDate fri, LocalDate sat, LocalDate sun, int year, Month month) {
        if (year > LocalDate.now().getYear() + 100 || year < LocalDate.now().getYear() - 99) {
            throw new IllegalArgumentException("Некорретный год. Доступный диапазон +/- 100 лет от текущей даты");
        }
        if (month == null) {
            throw new IllegalArgumentException("Параметр month не может быть null");
        }

        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.year = year;
        this.month = month;
    }

    public String getMon() {
        if (mon == null) {
            return "";
        } else {
            return String.valueOf(mon.getDayOfMonth());
        }
    }

    public String getTue() {
        if (tue == null) {
            return "";
        } else {
            return String.valueOf(tue.getDayOfMonth());
        }
    }

    public String getWed() {
        if (wed == null) {
            return "";
        } else {
            return String.valueOf(wed.getDayOfMonth());
        }
    }

    public String getThu() {
        if (thu == null) {
            return "";
        } else {
            return String.valueOf(thu.getDayOfMonth());
        }
    }

    public String getFri() {
        if (fri == null) {
            return "";
        } else {
            return String.valueOf(fri.getDayOfMonth());
        }
    }

    public String getSat() {
        if (sat == null) {
            return "";
        } else {
            return String.valueOf(sat.getDayOfMonth());
        }
    }

    public String getSun() {
        if (sun == null) {
            return "";
        } else {
            return String.valueOf(sun.getDayOfMonth());
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        WeekOfMonth w = (WeekOfMonth) obj;
        return getMon().equals(w.getMon())
                && getTue().equals(w.getTue())
                && getWed().equals(w.getWed())
                && getThu().equals(w.getThu())
                && getFri().equals(w.getFri())
                && getSat().equals(w.getSat())
                && getSun().equals(w.getSun())
                && year == w.year && month.equals(w.month);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.mon.hashCode()
                + this.tue.hashCode()
                + this.wed.hashCode()
                + this.thu.hashCode()
                + this.fri.hashCode()
                + this.sat.hashCode()
                + this.sun.hashCode()
                + year + month.hashCode();
        return hash;
    }
}