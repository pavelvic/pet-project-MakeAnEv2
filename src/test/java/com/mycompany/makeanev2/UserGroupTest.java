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
        
        Assert.assertEquals(expected,actual);
    }
}