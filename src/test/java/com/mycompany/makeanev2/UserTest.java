package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Exceptions.UserException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void applyChangesToUserTest() {

        final User user = new User(1, 2, "Администратор", "username", 0, "123", "example@example.com", "+7123456789", "Ivan", "Ivanov", "hi");
        final User changedUser = new User(1, 4, "Пользователь", "username2", 0, "321", "ex@example.com", "+7123759789", "John", "Fedorov", "hello");

        Map changes = new HashMap<>();
        changes.put("id_user", "1");
        changes.put("group_id", "4");
        changes.put("groupname", "Пользователь");
        changes.put("username", "username2");
        changes.put("password", "0");
        changes.put("passwordStr", "321");
        changes.put("email", "ex@example.com");
        changes.put("phone", "+7123759789");
        changes.put("name", "John");
        changes.put("surname", "Fedorov");
        changes.put("comment", "hello");
        user.applyChanges(changes);

        Assert.assertEquals(user.getId_user(), changedUser.getId_user());
        Assert.assertEquals(user.getGroup_id(), changedUser.getGroup_id());
        Assert.assertEquals(user.getGroupname(), changedUser.getGroupname());
        Assert.assertEquals(user.getUsername(), changedUser.getUsername());
        Assert.assertEquals(user.getPassword(), changedUser.getPassword());
        Assert.assertEquals(user.getPasswordStr(), changedUser.getPasswordStr());
        Assert.assertEquals(user.getEmail(), changedUser.getEmail());
        Assert.assertEquals(user.getPhone(), changedUser.getPhone());
        Assert.assertEquals(user.getName(), changedUser.getName());
        Assert.assertEquals(user.getSurname(), changedUser.getSurname());
        Assert.assertEquals(user.getComment(), changedUser.getComment());

    }

    @Test(expected = UserException.class)
    public void checkUserNameTest() throws UserException {
        User user;

        //пустое имя
        user = new User(0, 0, null, "", 0, null, null, null, null, null, null);
        user.checkUsernamePattern();

        //короткое имя до 2х символов
        user = new User(0, 0, null, "ab", 0, null, null, null, null, null, null);
        user.checkUsernamePattern();

        //слишком длинное
        user = new User(0, 0, null, "abcdefghijklmnopqrstuvwxyz", 0, null, null, null, null, null, null);
        user.checkUsernamePattern();

        //первый символ - буква
        user = new User(0, 0, null, "_bcdefg", 0, null, null, null, null, null, null);
        user.checkUsernamePattern();

        //русские буквы
        user = new User(0, 0, null, "abсdуеg", 0, null, null, null, null, null, null);
        user.checkUsernamePattern();
    }

    @Test(expected = UserException.class)
    public void checkPasswordPatternTest() throws UserException {
        User user;

        //короткий или пустой (менее 8 симв)
        String pw = "";
        for (int i = 0; i < 8; i++) {
            pw = "" + pw;
            user = new User(0, 0, null, null, 0, pw, null, null, null, null, null);
            user.checkPasswordPattern();
        }

        //нет букв в верхнем регистре
        user = new User(0, 0, null, "pass@wordq1", 0, null, null, null, null, null, null);
        user.checkPasswordPattern();

        //нет букв в нижнем регистре
        user = new User(0, 0, null, "PASS@WORDQ1", 0, null, null, null, null, null, null);
        user.checkPasswordPattern();

        //нет цифр
        user = new User(0, 0, null, "Pass@wordq", 0, null, null, null, null, null, null);
        user.checkPasswordPattern();

        //нет спецсимвола
        user = new User(0, 0, null, "Passwordq1", 0, null, null, null, null, null, null);
        user.checkPasswordPattern();

        //есть пробелы
        user = new User(0, 0, null, "Pass worDq1", 0, null, null, null, null, null, null);
        user.checkPasswordPattern();

        //есть кириллица
        user = new User(0, 0, null, "PаsswoЖDq1", 0, null, null, null, null, null, null);
        user.checkPasswordPattern();
    }

    @Test(expected = UserException.class)
    public void checkUserUniqueTest() throws UserException {

        /*метод checkUniqueUser(usersInBase, exceptUsers); используется для проверки уникальности при регистрации пользователя или его редактировании
        при регистрации - проверяем наличие пользователя в базе с такими же параметрами
        при редактировании - проверяем всех пользователей в базе на такие же параметры ИСКЛЮЧАЯ проверяемого (н исключая его, мы никогда не пройдем проверку корректно)
        checkUniqueUser(usersInBase, exceptUsers);
        usersInBase - все пользователи в базе
        exceptUsers - исключаемые из рассмотрения при проверке уникальности
         */
        final User userToAddOrChange = new User(1, 0, null, "username", 0, null, "example@example.com", "+7123456789", null, null, null);
        final User userInBase1 = new User(2, 0, null, "username", 0, null, null, null, null, null, null);
        final User userInBase2 = new User(3, 0, null, null, 0, null, "example@example.com", null, null, null, null);
        final User userInBase3 = new User(4, 0, null, null, 0, null, null, "+7123456789", null, null, null);
        final User userInBase4 = new User(1, 0, null, "username", 0, null, "example@example.com", "+7123456789", null, null, null);

        List<User> usersInBase = new ArrayList<>();
        usersInBase.add(userInBase1);
        usersInBase.add(userInBase2);
        usersInBase.add(userInBase3);
        usersInBase.add(userInBase4);

        List<User> exceptUsers = new ArrayList<>();
        exceptUsers.add(userToAddOrChange);

        userToAddOrChange.checkUniqueUser(usersInBase, exceptUsers);
    }
}
