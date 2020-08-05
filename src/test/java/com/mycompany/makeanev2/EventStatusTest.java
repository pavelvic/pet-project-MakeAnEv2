package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EventStatusTest {

    public EventStatusTest() {
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
    public void id1Test() {
        new EventStatus(-1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void id2Test() {
        new EventStatus(4, null);
    }

    @Test
    public void eventStatusesInBdTest() {
        /*для текущей реализации приложения критично, чтобы в базе данных находилось ровно три статуса события
        1 - Планируется;
        2 - Запланировано
        3 - Отменено
        перед сборкой мы убедимся, что необходмые статусы есть в базе*/

        List<EventStatus> expected = new ArrayList<>();
        expected.add(new EventStatus(1, "Планируется"));
        expected.add(new EventStatus(2, "Запланировано"));
        expected.add(new EventStatus(3, "Отменено"));

        List<EventStatus> actual = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/makeanevents?serverTimezone=UTC", "root", "1234")) {
            actual = EventDbQuery.selecAllStatuses(con);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void constructorsTest() throws SQLException {
        EventStatus expected = new EventStatus(1, "Планируется");
        EventStatus actual = new EventStatus();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void defaultValuesTest() throws SQLException {
        EventStatus constr1 = new EventStatus(1, "Планируется");
        EventStatus constr2 = new EventStatus();

        Assert.assertEquals("", constr1.getChecked());
        Assert.assertEquals("", constr2.getChecked());
    }

    @Test
    public void setCheckedTest() throws SQLException {
        EventStatus evs = new EventStatus();

        evs.setChecked();
        Assert.assertEquals("checked", evs.getChecked());

        evs.setNotChecked();
        Assert.assertEquals("", evs.getChecked());
    }

    @Test
    public void equalsAndHashCodeTest() {
        String name = "Статус";
        EventStatus es;
        EventStatus esi;
        EventStatus esj;

        List<EventStatus> eslist = new ArrayList<>();

        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                es = new EventStatus(j, name);
                eslist.add(es);
            }
            name = "Статус" + i;
        }

        es = new EventStatus(1, "Статус");

        Assert.assertTrue(eslist.get(0).equals(es));
        Assert.assertTrue(eslist.get(0).equals(eslist.get(0)));
        Assert.assertTrue(es.equals(eslist.get(0)));
        Assert.assertEquals(eslist.get(0).hashCode(), es.hashCode());
        Assert.assertEquals(es.hashCode(), eslist.get(0).hashCode());
        Assert.assertEquals(es.hashCode(), es.hashCode());

        for (int i = 0; i < eslist.size(); i++) {
            for (int j = 0; j < eslist.size(); j++) {
                esi = eslist.get(i);
                esj = eslist.get(j);
                if (esi != esj) {
                    Assert.assertFalse(esi.equals(esj));
                    Assert.assertNotEquals(esi.hashCode(), esj.hashCode());
                }
            }
        }
    }
}
