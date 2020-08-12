package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParticipantStatusTest {

    @Test
    public void participStatusesInBdTest() {
        /*для текущей реализации приложения критично, чтобы в базе данных находилось ровно два статуса участника
        1 - Основной;
        2 - Запасной
        перед сборкой мы убедимся, что необходмые статусы есть в базе*/

        List<ParticipantStatus> expected = new ArrayList<>();
        expected.add(new ParticipantStatus(1, "Основной"));
        expected.add(new ParticipantStatus(2, "Запасной"));

        List<ParticipantStatus> actual = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/makeanevents?serverTimezone=UTC", "root", "1234")) {
            actual = EventDbQuery.selecAllParticipantStatuses(con);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void defaultConstrucorStatusTest() {
        ParticipantStatus ps = new ParticipantStatus();
        Assert.assertEquals(1, ps.getId_participantStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rsNullTest() throws SQLException {
        new ParticipantStatus(null);
    }

    @Test
    public void setReserveStatusTest() {
        ParticipantStatus expected = new ParticipantStatus(2, "Запасной");

        ParticipantStatus actual = new ParticipantStatus();
        actual.setReserveStatus();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalsAndHashCodeTest() {
        String name = "Основной";
        ParticipantStatus ps;
        ParticipantStatus psi;
        ParticipantStatus psj;

        List<ParticipantStatus> pslist = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                ps = new ParticipantStatus(j, name);
                pslist.add(ps);
            }
            name = "Запасной";
        }

        ps = new ParticipantStatus(1, "Основной");

        Assert.assertTrue(pslist.get(0).equals(ps));
        Assert.assertTrue(pslist.get(0).equals(pslist.get(0)));
        Assert.assertTrue(ps.equals(pslist.get(0)));
        Assert.assertEquals(pslist.get(0).hashCode(), ps.hashCode());
        Assert.assertEquals(ps.hashCode(), pslist.get(0).hashCode());
        Assert.assertEquals(ps.hashCode(), ps.hashCode());

        for (int i = 0; i < pslist.size(); i++) {
            for (int j = 0; j < pslist.size(); j++) {
                psi = pslist.get(i);
                psj = pslist.get(j);
                if (psi != psj) {
                    Assert.assertFalse(psi.equals(psj));
                    Assert.assertNotEquals(psi.hashCode(), psj.hashCode());
                }
            }
        }
    }
}
