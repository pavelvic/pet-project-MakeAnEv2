package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Exceptions.ParticipantException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Test;
import org.junit.Assert;

public class EventTest {

    private final Random random = new Random();

    //null аргумент для конструктора
    @Test(expected = IllegalArgumentException.class)
    public void nullRsTest() throws SQLException {
        new Event(null);
    }

    //исключение при отрицательном id
    @Test(expected = IllegalArgumentException.class)
    public void id_eventTest() {
        new Event((random.nextInt(Integer.MAX_VALUE - 1) + 1) * (-1), null, null, null, null, null, null, 0, null, null, null);
    }

    //проверка консутруктора, должен выдавать исключение при отрицательном количестве участников
    @Test(expected = IllegalArgumentException.class)
    public void maxParticipantsTest() {
        new Event(1, null, null, null, null, null, null, (random.nextInt(Integer.MAX_VALUE - 1) + 1) * (-1), null, null, null);
    }

    //попытка установить для события список участников с более одним автором
    @Test(expected = EventException.class)
    public void more1AuthorInPartitipantListTest() throws EventException {
        List<Participant> plist = new ArrayList<>();
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);

        plist.add(new Participant(0, null, null, null, 1, null));
        plist.add(new Participant(0, null, null, null, 1, null));

        ev.setParticipants(plist);
    }

    //попытка установить для события список участников, не содержащий автора
    @Test(expected = EventException.class)
    public void noAuthorInPartitipantListTest() throws EventException {
        List<Participant> plist = new ArrayList<>();
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);

        plist.add(new Participant(0, null, null, null, 0, null));

        ev.setParticipants(plist);
    }

    //проверка на null выдачи листа участников по умолчанию
    @Test
    public void nullParticipantListTest() {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        Assert.assertNull(ev.getParticipants());
    }

    //проверка countOfParticipants на установку по умолчанию - 0
    @Test
    public void countOfParticipIsZeroTest() {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        int expected = 0;
        int actual = ev.getCountOfParticipants();
        Assert.assertEquals(expected, actual);
    }

    //проверка countOfParticipants, должно выдать количество участников
    @Test
    public void countOfParticipTest() throws EventException {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        List<Participant> lp = new ArrayList<Participant>();
        lp.add(new Participant(0, null, null, null, 1, null));
        ev.setParticipants(lp);
        int expected = 1;
        int actual = ev.getCountOfParticipants();
        Assert.assertEquals(expected, actual);
    }

    //тест формата вывода даты (используется для вывода в html)
    @Test
    public void getEventTimeTest() {
        Event ev = new Event(0, null, null, null, null, null, ZonedDateTime.of(2020, 8, 6, 23, 41, 0, 0, ZoneId.of("UTC")), 0, null, null, null);
        String expected = "06.08.2020 23:41";
        String actual = ev.getEventTime();
        Assert.assertEquals(expected, actual);
    }

    //тест формата вывода даты (используется для вывода в html)
    @Test
    public void getCreateTimeTest() {
        Event ev = new Event(0, null, null, null, null, null, null, 0, ZonedDateTime.of(2020, 8, 6, 23, 41, 0, 0, ZoneId.of("UTC")), null, ZoneId.of("UTC"));
        String expected = "06.08.2020 23:41";
        String actual = ev.getCreateTime();
        Assert.assertEquals(expected, actual);
    }

    //проверка вызова метода с пустым параметром zone
    @Test(expected = IllegalArgumentException.class)
    public void zoneIsNullTest() {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        ev.getCreateTime();
    }

    //тест формата вывода даты (используется для вывода в html)
    @Test
    public void getCritTimeTest() {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, ZonedDateTime.of(2020, 8, 6, 23, 41, 0, 0, ZoneId.of("UTC")), null);
        String expected = "06.08.2020 23:41";
        String actual = ev.getCritTime();
        Assert.assertEquals(expected, actual);
    }

    //setParticipants - проверка аргумента при нулл
    @Test(expected = IllegalArgumentException.class)
    public void setParticipantsNullTest() throws EventException {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        ev.setParticipants(null);
    }

    //checkTimes() - проверка трёх случаев: дата события в прошлом, критическая дата позже даты события, критическая дата в прошлом
    @Test(expected = EventException.class)
    public void checkEventTimeInPastTest() throws EventException {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        ZonedDateTime eventTimeBefore = ZonedDateTime.now(ZoneId.of("UTC")).minusDays(random.nextInt(360) - 1);
        Event ev = new Event(0, null, null, null, null, null, eventTimeBefore, 0, now, null, null);
        ev.checkTimes();
    }

    @Test(expected = EventException.class)
    public void checkCritTimeBeforeEventTimeTest() throws EventException {
        ZonedDateTime critTime = ZonedDateTime.now(ZoneId.of("UTC")).plusDays(random.nextInt(360) + 1);
        ZonedDateTime eventTime = ZonedDateTime.now(ZoneId.of("UTC"));
        Event ev = new Event(0, null, null, null, null, null, eventTime, 0, null, critTime, null);
        ev.checkTimes();
    }

    @Test(expected = EventException.class)
    public void checkCritTimeInPastTest() throws EventException {
        ZonedDateTime critTime = ZonedDateTime.now(ZoneId.of("UTC")).minusDays(random.nextInt(360) - 1);
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        Event ev = new Event(0, null, null, null, null, null, now, 0, null, critTime, null);
        ev.checkTimes();
    }

    //checkEventStatus() - исключения при вызове метода на объектах с id ==2 и id ==3
    @Test(expected = EventException.class)
    public void checkEventStatus2Test() throws EventException {
        Event ev = new Event(0, new EventStatus(2, null), null, null, null, null, null, 0, null, null, null);
        ev.checkEventStatus();
    }

    @Test(expected = EventException.class)
    public void checkEventStatus3Test() throws EventException {
        Event ev = new Event(0, new EventStatus(3, null), null, null, null, null, null, 0, null, null, null);
        ev.checkEventStatus();
    }

    //checkEventRegStatus() - исключение при вызове метода на объекте со статусом 2
    @Test(expected = EventException.class)
    public void checkEventRegStatus2Test() throws EventException {
        Event ev = new Event(0, null, new EventRegStatus(2, null), null, null, null, null, 0, null, null, null);
        ev.checkEventRegStatus();
    }

    //findAuthor()  - передан null, возвращаем null
    @Test
    public void findAuthorNullParticipantListTest() {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        Assert.assertNull(ev.findAuthor());
    }

    //в списке нет авторов - Exception
    @Test(expected = EventException.class)
    public void findAuthorParticipantListNoAuthorsTest() throws EventException {
        List<Participant> plist = new ArrayList<>();
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        plist.add(new Participant(0, null, null, null, 0, null));
        ev.setParticipants(plist);
    }

    //в списке есть автор - возращаем первого найденного
    @Test
    public void findAuthorParticipantListHasAuthorTest() throws EventException {
        List<Participant> plist = new ArrayList<>();
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        Participant expected = new Participant(0, null, null, null, 1, null);

        plist.add(expected);
        plist.add(new Participant(0, null, null, null, 0, null));
        ev.setParticipants(plist);
        Participant actual = ev.findAuthor();

        Assert.assertEquals(expected, actual);
    }

    //аргумент user null
    @Test(expected = IllegalArgumentException.class)
    public void getParticipantByPersonUserNullTest() throws ParticipantException {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        ev.getParticipantByPerson(null);
    }

    //participantList null
    @Test(expected = IllegalArgumentException.class)
    public void getParticipantByPersonListNullTest() throws ParticipantException {
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        ev.getParticipantByPerson(new User(1, 4, null, null, 0, null, null, null, null, null, null));
    }

    //пользователь не участвует в событии
    @Test(expected = ParticipantException.class)
    public void getParticipantByPersonNotParticipTest() throws ParticipantException, EventException {
        List<Participant> plist = new ArrayList<>();
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        User userInEv = new User(1, 4, null, null, 0, null, null, null, null, null, null);
        User userNotInEv = new User(2, 4, null, null, 0, null, null, null, null, null, null);
        Participant pc = new Participant(0, userInEv, null, null, 1, null);

        plist.add(pc);
        ev.setParticipants(plist);

        ev.getParticipantByPerson(userNotInEv);
    }

    //пользователь является участником события
    @Test
    public void getParticipantByPersonHasParticipTest() throws ParticipantException, EventException {
        List<Participant> plist = new ArrayList<>();
        Event ev = new Event(0, null, null, null, null, null, null, 0, null, null, null);
        User user = new User(1, 4, null, null, 0, null, null, null, null, null, null);

        Participant expected = new Participant(0, user, null, null, 1, null);

        plist.add(expected);
        ev.setParticipants(plist);

        Assert.assertEquals(expected, ev.getParticipantByPerson(user));
        Assert.assertEquals(expected, ev.getAuthor());
    }

    @Test
    public void gettersAndSettersTest() throws SQLException {
        EventStatus expEvStatus = new EventStatus(1, "Планируется");
        EventRegStatus expEvRegStatus = new EventRegStatus(1, "Открыта");
        ZonedDateTime expEvTime = ZonedDateTime.of(2020, 8, 30, 23, 41, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime expCreateTime = ZonedDateTime.of(2020, 8, 10, 23, 41, 0, 0, ZoneId.of("UTC"));
        ZonedDateTime expCritTime = ZonedDateTime.of(2020, 8, 25, 23, 41, 0, 0, ZoneId.of("UTC"));
        ZoneId expZoneId = ZoneId.of("UTC");

        Event ev = new Event(1, expEvStatus, expEvRegStatus, "name", "descr", "place", expEvTime, 5, expCreateTime, expCritTime, expZoneId);

        Assert.assertEquals(1, ev.getId_event());
        Assert.assertEquals(expEvStatus, ev.getEvStatus());
        Assert.assertEquals(expEvRegStatus, ev.getEvRegStatus());
        Assert.assertEquals("name", ev.getName());
        Assert.assertEquals("descr", ev.getDescription());
        Assert.assertEquals("place", ev.getPlace());
        Assert.assertEquals(expEvTime, ev.getZonedEventTime());
        Assert.assertEquals(expCreateTime, ev.getZonedCreateTime());
        Assert.assertEquals(expCritTime, ev.getZonedCritTime());
        Assert.assertEquals(5, ev.getMaxParticipants());
        Assert.assertEquals(expZoneId, ev.getZone());

        ev.setZone(ZoneId.of("GMT"));
        Assert.assertEquals(ZoneId.of("GMT"), ev.getZone());
    }
}
