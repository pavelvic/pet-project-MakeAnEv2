package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

public class EventRegStatusTest {

    private final Random random = new Random();

    //id !=1 !=2 недопустимы
    @Test(expected = IllegalArgumentException.class)
    public void idTest() {
        int n = random.nextInt();
        while (n == 1 || n == 2) {
            n = random.nextInt();
        }
        new EventRegStatus(n, null);
    }

    //проверкан а null конструктора с ResultSet
    @Test(expected = IllegalArgumentException.class)
    public void rsNullTest() throws SQLException {
        new EventRegStatus(null);
    }

    @Test
    public void eventRegStatusesInDBTest() {
        /*для текущей реализации приложения критично, чтобы в базе данных находилось ровно два статуса регистрации события
        1 - Открыта;
        2 - Закрыта
        перед сборкой мы убедимся, что необходмые статусы есть в базе*/

        List<EventRegStatus> expected = new ArrayList<>();
        expected.add(new EventRegStatus(1, "Открыта"));
        expected.add(new EventRegStatus(2, "Закрыта"));

        List<EventRegStatus> actual = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/makeanevents?serverTimezone=UTC", "root", "1234")) {
            actual = EventDbQuery.selectEventStatuses(con);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Assert.assertEquals(expected, actual);
    }

    //соблюдение контракта equals-hashcode
    @Test
    public void equalsAndHashCodeTest() {
        String name = "Открыта";
        EventRegStatus erg;
        EventRegStatus ergi;
        EventRegStatus ergj;

        List<EventRegStatus> erglist = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                erg = new EventRegStatus(j, name);
                erglist.add(erg);
            }
            name = "Закрыта";
        }

        erg = new EventRegStatus(1, "Открыта");

        Assert.assertTrue(erglist.get(0).equals(erg));
        Assert.assertTrue(erg.equals(erglist.get(0)));
        Assert.assertTrue(erglist.get(0).equals(erglist.get(0)));
        Assert.assertEquals(erglist.get(0).hashCode(), erg.hashCode());
        Assert.assertEquals(erg.hashCode(), erglist.get(0).hashCode());
        Assert.assertEquals(erg.hashCode(), erg.hashCode());

        for (int i = 0; i < erglist.size(); i++) {
            for (int j = 0; j < erglist.size(); j++) {
                ergi = erglist.get(i);
                ergj = erglist.get(j);
                if (ergi != ergj) {
                    Assert.assertFalse(ergi.equals(ergj));
                    Assert.assertNotEquals(ergi.hashCode(), ergj.hashCode());
                }
            }
        }
    }
}
