package com.mycompany.makeanev2;

import java.time.LocalDate;
import java.time.Month;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WeekOfMonthTest {

    public WeekOfMonthTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullMonthTest() {
        new WeekOfMonth(LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void yearMinBorderTest() {
        LocalDate.now().getYear();
        int year = LocalDate.now().getYear() - 101;
        new WeekOfMonth(LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, year, Month.FEBRUARY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void yearMaxBorderTest() {
        LocalDate.now().getYear();
        int year = LocalDate.now().getYear() + 101;
        new WeekOfMonth(LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, LocalDate.MIN, year, Month.FEBRUARY);
    }

    @Test
    public void getNullDayTest() {
        //гет методы при null должны возвращать пустую строку, поскольку она печатается на старнице
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
}
