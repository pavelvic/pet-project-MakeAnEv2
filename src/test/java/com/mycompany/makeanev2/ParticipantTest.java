package com.mycompany.makeanev2;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.Assert;
import org.junit.Test;

public class ParticipantTest {

    final private Participant pc = new Participant(null, null, null, null);

    @Test(expected = IllegalArgumentException.class)
    public void idTest() {
        new Participant(-1, null, null, null, 0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isAuthorTest() {
        new Participant(0, null, null, null, -1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isAuthor2Test() {
        new Participant(0, null, null, null, 2, null);
    }

    @Test
    public void defaultValueIDTest() {
        Assert.assertEquals(0, pc.getId_participant());
    }

    @Test
    public void defaultValueZoneTest() {
        Assert.assertTrue(ZoneId.of("UTC").equals(pc.getZone()));
    }

    //вывод должен быть читаемым
    @Test
    public void getRegDatetimeTest() {
        Participant p = new Participant(null, null, null, ZonedDateTime.of(2020, 8, 4, 23, 21, 0, 0, ZoneId.of("UTC")));

        String expected = "04.08.2020 23:21";
        String actual = p.getRegDatetime();

        Assert.assertEquals(expected, actual);
    }
}
