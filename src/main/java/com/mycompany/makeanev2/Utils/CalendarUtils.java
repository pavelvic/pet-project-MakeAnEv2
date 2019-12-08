package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.WeekOfMonth;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CalendarUtils {

    public static List<WeekOfMonth> getWeeksOfMonth(int year, Month month) {
        List<WeekOfMonth> weeks = new ArrayList<>();

        LocalDate date;
        date = LocalDate.of(year, month, 1);
        
            LocalDate Mon;
            LocalDate Tue;
            LocalDate Wed;
            LocalDate Thu;
            LocalDate Fri;
            LocalDate Sat;
            LocalDate Sun;

            LocalDate endDate = LocalDate.of(year, month, date.lengthOfMonth());
            
        while (date.compareTo(endDate) <=0) {

            if ( (date.getDayOfWeek() == DayOfWeek.MONDAY) & (date.compareTo(endDate) <=0) ) {
                Mon = date;
                date = date.plusDays(1);
            } else {
                Mon = null;
            }

            

            if ( (date.getDayOfWeek() == DayOfWeek.TUESDAY) & (date.compareTo(endDate) <=0) ) {
                Tue = date;
                date = date.plusDays(1);
            } else {
                Tue = null;
            }


            if ( (date.getDayOfWeek() == DayOfWeek.WEDNESDAY) & (date.compareTo(endDate) <=0) ) {
                Wed = date;
                date = date.plusDays(1);
            } else {
                Wed = null;
            }

            

            if ( (date.getDayOfWeek() == DayOfWeek.THURSDAY) & (date.compareTo(endDate) <=0) ) {
                Thu = date;
                date = date.plusDays(1);
            } else {
                Thu = null;
            }

            

            if ( (date.getDayOfWeek() == DayOfWeek.FRIDAY) & (date.compareTo(endDate) <=0) ) {
                Fri = date;
                date = date.plusDays(1);
            } else {
                Fri = null;
            }

            

            if ( (date.getDayOfWeek() == DayOfWeek.SATURDAY) & (date.compareTo(endDate) <=0) ) {
                Sat = date;
                date = date.plusDays(1);
            } else {
                Sat = null;
            }

            

            if ( (date.getDayOfWeek() == DayOfWeek.SUNDAY) & (date.compareTo(endDate) <=0) ) {
                Sun = date;
                date = date.plusDays(1);
            } else {
                Sun = null;
            }
            
            WeekOfMonth week = new WeekOfMonth(Mon, Tue, Wed, Thu, Fri, Sat, Sun, year, month);
            weeks.add(week);    
        }
        return weeks;
    }
}
