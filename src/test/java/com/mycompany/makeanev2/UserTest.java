package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Exceptions.UserException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    private final User userToAddOrChange = new User(1, 0, null, "username", 0, null, "example@example.com", "+7123456789", null, null, null);
    private final List<User> exceptUsers = Arrays.asList(userToAddOrChange);

    @Test(expected = IllegalArgumentException.class)
    public void applyChangesNullTest() {
        userToAddOrChange.applyChanges(null);
    }

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

    /*метод checkUniqueUser(usersInBase, exceptUsers); используется для проверки уникальности при регистрации пользователя или его редактировании
        при регистрации - проверяем наличие пользователя в базе с такими же параметрами
        при редактировании - проверяем всех пользователей в базе на такие же параметры ИСКЛЮЧАЯ проверяемого (не исключая его, мы никогда не пройдем проверку корректно)
        checkUniqueUser(usersInBase, exceptUsers);
        usersInBase - все пользователи в базе
        exceptUsers - исключаемые из рассмотрения при проверке уникальности
     */
    @Test
    public void checkUserUniqueSelfTest() throws UserException {
        List<User> usersInBase = new ArrayList<>();
        usersInBase.add(userToAddOrChange);

        userToAddOrChange.checkUniqueUser(usersInBase, exceptUsers);
    }

    @Test(expected = UserException.class)
    public void checkUserUniqueUsernameTest() throws UserException {
        final User userInBase1 = new User(2, 0, null, "username", 0, null, null, null, null, null, null);

        List<User> usersInBase = new ArrayList<>();
        usersInBase.add(userInBase1);

        userToAddOrChange.checkUniqueUser(usersInBase, exceptUsers);
    }

    @Test(expected = UserException.class)
    public void checkUserUniqueEmailTest() throws UserException {
        final User userInBase1 = new User(3, 0, null, null, 0, null, "example@example.com", null, null, null, null);

        List<User> usersInBase = new ArrayList<>();
        usersInBase.add(userInBase1);

        userToAddOrChange.checkUniqueUser(usersInBase, exceptUsers);
    }

    @Test(expected = UserException.class)
    public void checkUserUniqueTelTest() throws UserException {
        final User userInBase1 = new User(4, 0, null, null, 0, null, null, "+7123456789", null, null, null);

        List<User> usersInBase = new ArrayList<>();
        usersInBase.add(userInBase1);

        userToAddOrChange.checkUniqueUser(usersInBase, exceptUsers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUserUniqueNull1stTest() throws UserException {
        userToAddOrChange.checkUniqueUser(null, exceptUsers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUserUniqueNull2stTest() throws UserException {
        userToAddOrChange.checkUniqueUser(exceptUsers, null);
    }

    @Test
    public void equalsAndHashCodeTest() {
        User us1 = new User(1, 0, null, null, 0, null, null, null, null, null, null);
        User us2 = new User(1, 2, null, null, 3, null, null, null, null, null, null);
        User us3 = new User(2, 5, null, null, 4, null, null, null, null, null, null);

        Assert.assertTrue(us1.equals(us1));
        Assert.assertTrue(us1.equals(us2));
        Assert.assertTrue(us2.equals(us1));
        Assert.assertFalse(us1.equals(us3));
        Assert.assertFalse(us3.equals(us1));

        Assert.assertEquals(us1.hashCode(), us1.hashCode());
        Assert.assertEquals(us1.hashCode(), us2.hashCode());
        Assert.assertEquals(us2.hashCode(), us1.hashCode());
        Assert.assertNotEquals(us1.hashCode(), us3.hashCode());
        Assert.assertNotEquals(us3.hashCode(), us1.hashCode());
    }
}
