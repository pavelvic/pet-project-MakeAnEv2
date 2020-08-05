package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Utils.UserDbQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class UserGroupTest {

    @Test
    public void UserGroupsInBdTest() {
        /*для приложения критично, чтобы в базе данных находилось ровно пять групп пользователей
        1 - суперпользователи;
        2 - администраторы
        3 - менеджеры
        4 - пользователи
        5 - заблокированные
        перед сборкой мы убедимся, что необходмые группы есть в базе*/

        List<UserGroup> expected = new ArrayList<>();
        expected.add(new UserGroup(1, "Суперпользователь"));
        expected.add(new UserGroup(2, "Администратор"));
        expected.add(new UserGroup(3, "Менеджер"));
        expected.add(new UserGroup(4, "Пользователь"));
        expected.add(new UserGroup(5, "Заблокирован"));

        List<UserGroup> actual = null;
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost/makeanevents?serverTimezone=UTC", "root", "1234")) {
            actual = UserDbQuery.selectUserGroup(con);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalsAndHashCodeTest() {
        String name = "Пользователь";
        UserGroup ug;
        UserGroup ugi;
        UserGroup ugj;

        List<UserGroup> uglist = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                ug = new UserGroup(j, name);
                uglist.add(ug);
            }
            name = "Суперпользователь";
        }

        ug = new UserGroup(1, "Пользователь");

        Assert.assertTrue(uglist.get(0).equals(ug));
        Assert.assertTrue(ug.equals(uglist.get(0)));
        Assert.assertTrue(uglist.get(0).equals(uglist.get(0)));
        Assert.assertEquals(uglist.get(0).hashCode(), ug.hashCode());
        Assert.assertEquals(ug.hashCode(), uglist.get(0).hashCode());
        Assert.assertEquals(ug.hashCode(), ug.hashCode());

        for (int i = 0; i < uglist.size(); i++) {
            for (int j = 0; j < uglist.size(); j++) {
                ugi = uglist.get(i);
                ugj = uglist.get(j);
                if (ugi != ugj) {
                    Assert.assertFalse(ugi.equals(ugj));
                    Assert.assertNotEquals(ugi.hashCode(), ugj.hashCode());
                }
            }
        }
    }
}
