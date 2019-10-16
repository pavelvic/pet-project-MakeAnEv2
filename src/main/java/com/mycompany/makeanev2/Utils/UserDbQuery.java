package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.UserGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*класс запросов к БД*/
public class UserDbQuery {

    //найти пользователя в БД по логину и паролю (актуально для аутентификации)
    public static User findUser(Connection con, String username, String password) throws SQLException {
        String sql = "SELECT u.id_user, u.group_id, g.Name, u.username, u.password, u.email, u.phone, u.name, u.surname, u.comment FROM `user` u, `usergroups` g "
                + "WHERE g.id_group = u.group_id "
                + "AND u.username = ? AND u.password = ?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, username);
        int hashPassword = password.hashCode();
        pstm.setInt(2, hashPassword);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return new User(rs);
        }
        return null;
    }

    //получить пользователя из БД по логину (актуально для случаев, когда прошла аутентификация)
    public static User findUser(Connection con, String username) throws SQLException {
        //в подходах java определено использовать символ "?" как параметр sql запроса
        String sql = "SELECT u.id_user, u.group_id, g.Name, u.username, u.password, u.email, u.phone, u.name, u.surname, u.comment FROM `user` u, `usergroups` g "
                + "WHERE g.id_group = u.group_id "
                + "AND u.username = ?";

        PreparedStatement pstm = con.prepareStatement(sql);

        //установка параметров
        pstm.setString(1, username);
        
        //выполнение и результат в курсоре rs
        ResultSet rs = pstm.executeQuery();

        //вывод курсора как результат функции
        if (rs.next()) {
            return new User(rs);
        }
        return null;
    }

    //отдельный список групп пользователей и их названий нужен для вывода на страницах редактирования групп пользователей
    //для обеспечения выпадающих списков
    public static List<UserGroup> selectUserGroup(Connection con) throws SQLException {
        String sql = "SELECT id_group, Name FROM usergroups ORDER BY id_group ASC";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();
        List<UserGroup> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new UserGroup(rs));
        }
        return list;
    }

    //добавление нового пользователя в БД на основе объекта, актуально при регистрации пользователя
    public static void insertUser(Connection con, User user) throws SQLException {
        
        //символ "?" как параметр sql запроса
        String sql = "INSERT INTO user(group_id, username,  password, email, phone, name, surname, comment) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        //здесь определяем параметры (символ "?") для sql-запроса на основе объекта User
        ps.setInt(1, user.getGroup_id());
        ps.setString(2, user.getUsername());
        ps.setInt(3, user.getPassword());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getName());
        ps.setString(7, user.getSurname());
        ps.setString(8, user.getComment());

        //ВЫПОЛНЯЕМ sql-запрос
        ps.executeUpdate();
    }

    //получение в коллекции всего списка пользователей из БД в виде объектов (актуально при выводе списков пользователей и проверке на уникальность новых и редактируемых пользователей)
    public static List<User> selectUser(Connection con) throws SQLException {
        
        String sql = "SELECT u.id_user, u.group_id, g.Name, u.username, u.password, u.email, u.phone, u.name, u.surname, u.comment "
                + "FROM `user` u, `usergroups` g "
                + "WHERE g.id_group = u.group_id";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();
        List<User> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new User(rs));
        }
        return list;
    }

    /*перегруженный метод, возвращающий 1 одного пользователя по id, нужен при просмотре пользователя, редактировании, удалении
    когда переходим на страницу, из параметров в наличии только айди, по которому мы конструируем из БД сам объект User для даль
    нейших манипуляций*/
    public static User selectUser(Connection con, String id_userStr) throws SQLException {
        
        String sql = "SELECT u.id_user, u.group_id, g.Name, u.username, u.password, u.email, u.phone, u.name, u.surname, u.comment "
                + "FROM `user` u, `usergroups` g "
                + "WHERE g.id_group = u.group_id "
                + "AND u.id_user = ?";

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setString(1, id_userStr);

        ResultSet rs = ptsm.executeQuery();

        if (rs.next()) {
            return new User(rs);
        }
        return null;
    }
    
    /*обновляем информацию о пользователе в БД, при различных пользовательских редактированиях*/
    public static void updateUser(Connection con, User user) throws SQLException {
        
        String sql = "UPDATE user SET group_id = ?, username = ?, email = ?, phone = ?, name = ?, surname = ?, comment = ? WHERE id_user = ?"; //? - параметр, подставляем из экземпляра

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, user.getGroup_id());
        ptsm.setString(2, user.getUsername()); //первый параметр в SQL-запросе (?)
        ptsm.setString(3, user.getEmail()); //второй и так далее
        ptsm.setString(4, user.getPhone());
        ptsm.setString(5, user.getName());
        ptsm.setString(6, user.getSurname());
        ptsm.setString(7, user.getComment());
        ptsm.setInt(8, user.getId_user());

        ptsm.executeUpdate();
    }

    /*обновляем пароль пользователя (для функционала установки нового пароля)*/
    public static void updateUserPassword(Connection con, User user) throws SQLException {
        /*обновляем пароль для пользователя*/
        String sql = "UPDATE user SET password = ? WHERE id_user = ?";

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, user.getPassword());
        ptsm.setInt(2, user.getId_user());

        ptsm.executeUpdate();

    }
    //TODO: переписать этот метод на отдельный resetUserPassword, разораться почему это сделано по id_user, а не объектом
    //сброс пароля пользователя
    public static void updateUserPassword(Connection con, String id_user) throws SQLException {
        /*обновляем пароль для пользователя*/
        String sql = "UPDATE user SET password = ? WHERE id_user = ?";
        String pass = "0"; //пароль по умолчанию при сбросе
        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, pass.hashCode());
        ptsm.setString(2, id_user);

        ptsm.executeUpdate();

    }

    /*удаляем пользователя из БД*/
    public static void deleteUser(Connection con, User user) throws SQLException, NumberFormatException {
        /*Удаляем запись пользователя*/
        String sql = "DELETE FROM user WHERE id_user=?";

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, user.getId_user());

        ptsm.executeUpdate();
    }
}