package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.WeekOfMonth;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalendarUtilsTest {

    @Test(expected = IllegalArgumentException.class)
    public void nullMonthTest() {
        Month month = null;
        CalendarUtils.getWeeksOfMonth(2020, month);
    }

    @Test(expected = IllegalArgumentException.class)
    public void yearMinBorderTest() {
        LocalDate.now().getYear();
        int year = LocalDate.now().getYear() - 101;
        Month month = Month.FEBRUARY;
        CalendarUtils.getWeeksOfMonth(year, month);
    }

    @Test(expected = IllegalArgumentException.class)
    public void yearMaxBorderTest() {
        LocalDate.now().getYear();
        int year = LocalDate.now().getYear() + 101;
        Month month = Month.FEBRUARY;
        CalendarUtils.getWeeksOfMonth(year, month);
    }

    /*необходимо проверить корректность формирования календаря с днями неделями для случаев:
    - в месяце 28/29/30/31 дней (первый день может начинаться с понедельника, вторника, среды, четверга, пятницы, субботы) - итого 7*4=28 случаев*/
    @Test
    public void getWeeksOfMonth28DaysFromMonTest() {
        /*проверяем корректность метода для 28 дней и если первое число выпадает на понедельник, остальные тестовые методы аналогичны*/
        //01.02.2010
        int year = 2010;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth28DaysFromTueTest() {

        //01.02.2011
        int year = 2011;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), null, null, null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth28DaysFromWedTest() {

        //01.02.2017
        int year = 2017;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth28DaysFromThuTest() {

        //01.02.2018
        int year = 2018;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth28DaysFromFriTest() {

        //01.02.2019
        int year = 2019;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth28DaysFromSatTest() {

        //01.02.2014
        int year = 2014;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth28DaysFromSunTest() {

        //01.02.2015
        int year = 2015;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, null, null, LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth29DaysFromMonTest() {

        //01.02.2016
        int year = 2016;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), null, null, null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth29DaysFromTueTest() {

        //01.02.2020
        int year = 2000;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth29DaysFromWedTest() {

        //01.02.2020
        int year = 2012;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth29DaysFromThuTest() {

        //01.02.2020
        int year = 1996;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth29DaysFromFriTest() {

        //01.02.2020
        int year = 2008;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth29DaysFromSatTest() {

        //01.02.2020
        int year = 2020;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth29DaysFromSunTest() {

        //01.02.2020
        int year = 2004;
        int day = 0;
        Month month = Month.FEBRUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, null, null, LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth30DaysFromMonTest() {

        //01.06.2020
        int year = 2020;
        int day = 0;
        Month month = Month.JUNE;

        WeekOfMonth w1 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth30DaysFromTueTest() {

        //01.09.2020
        int year = 2020;
        int day = 0;
        Month month = Month.SEPTEMBER;

        WeekOfMonth w1 = new WeekOfMonth(null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth30DaysFromWedTest() {

        //01.04.2020
        int year = 2020;
        int day = 0;
        Month month = Month.APRIL;

        WeekOfMonth w1 = new WeekOfMonth(null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth30DaysFromThuTest() {

        //01.11.2018
        int year = 2018;
        int day = 0;
        Month month = Month.NOVEMBER;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth30DaysFromFriTest() {

        //01.11.2019
        int year = 2019;
        int day = 0;
        Month month = Month.NOVEMBER;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth30DaysFromSatTest() {

        //01.09.2018
        int year = 2018;
        int day = 0;
        Month month = Month.SEPTEMBER;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth30DaysFromSunTest() {

        //01.11.2020
        int year = 2020;
        int day = 0;
        Month month = Month.NOVEMBER;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, null, null, LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w6 = new WeekOfMonth(LocalDate.of(year, month, ++day), null, null, null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5, w6);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth31DaysFromMonTest() {

        //01.07.2019
        int year = 2019;
        int day = 0;
        Month month = Month.JULY;

        WeekOfMonth w1 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth31DaysFromTueTest() {

        //01.12.2020
        int year = 2020;
        int day = 0;
        Month month = Month.DECEMBER;

        WeekOfMonth w1 = new WeekOfMonth(null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth31DaysFromWedTest() {

        //01.12.2020
        int year = 2020;
        int day = 0;
        Month month = Month.JANUARY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth31DaysFromThuTest() {

        //01.10.2020
        int year = 2020;
        int day = 0;
        Month month = Month.OCTOBER;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth31DaysFromFriTest() {

        //01.05.2020
        int year = 2020;
        int day = 0;
        Month month = Month.MAY;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth31DaysFromSatTest() {

        //01.08.2020
        int year = 2020;
        int day = 0;
        Month month = Month.AUGUST;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, null, LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w6 = new WeekOfMonth(LocalDate.of(year, month, ++day), null, null, null, null, null, null, year, month);

        //LocalDate.of(year, month, ++day)
        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5, w6);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }

    @Test
    public void getWeeksOfMonth31DaysFromSunTest() {

        //01.08.2020
        int year = 2020;
        int day = 0;
        Month month = Month.MARCH;

        WeekOfMonth w1 = new WeekOfMonth(null, null, null, null, null, null, LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w2 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w3 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w4 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w5 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), year, month);
        WeekOfMonth w6 = new WeekOfMonth(LocalDate.of(year, month, ++day), LocalDate.of(year, month, ++day), null, null, null, null, null, year, month);

        List<WeekOfMonth> expected = Arrays.asList(w1, w2, w3, w4, w5, w6);
        List<WeekOfMonth> actual = CalendarUtils.getWeeksOfMonth(year, month);

        assertEquals(expected, actual);
    }
}
