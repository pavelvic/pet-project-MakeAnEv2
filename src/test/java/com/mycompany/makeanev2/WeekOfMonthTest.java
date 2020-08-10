package com.mycompany.makeanev2;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Assert;
import org.junit.Test;

public class WeekOfMonthTest {

    private final Random random = new Random();

    @Test(expected = IllegalArgumentException.class)
    public void nullMonthTest() {
        new WeekOfMonth(LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, 0, null);
    }

    //допустимые границы работы метода - 100 лет
    @Test(expected = IllegalArgumentException.class)
    public void yearMinBorderTest() {
        LocalDate.now().getYear();
        int year = LocalDate.now().getYear() - 101 - random.nextInt(Integer.MAX_VALUE);
        new WeekOfMonth(LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, year, Month.FEBRUARY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void yearMaxBorderTest() {
        LocalDate.now().getYear();
        int year = LocalDate.now().getYear() + 101 + random.nextInt(Integer.MAX_VALUE);
        new WeekOfMonth(LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, year, Month.FEBRUARY);
    }

    //для корректного вывода, null должен выводится как ""
    @Test
    public void getNullDayTest() {
        WeekOfMonth wof = new WeekOfMonth(null, null, null, null, null, null, null, 2020, Month.FEBRUARY);

        String Mon = wof.getMon();
        String Tue = wof.getTue();
        String Wed = wof.getWed();
        String Thu = wof.getThu();
        String Fri = wof.getFri();
        String Sat = wof.getSat();
        String Sun = wof.getSun();

        Assert.assertEquals("", Mon);
        Assert.assertEquals("", Tue);
        Assert.assertEquals("", Wed);
        Assert.assertEquals("", Thu);
        Assert.assertEquals("", Fri);
        Assert.assertEquals("", Sat);
        Assert.assertEquals("", Sun);
    }

    //контракт euqals и hashcode
    @Test
    public void equalsAndHashCodeTest() {
        EqualsVerifier.forClass(WeekOfMonth.class).verify();
    }
}
