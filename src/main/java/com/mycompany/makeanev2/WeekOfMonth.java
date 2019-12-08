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

}
