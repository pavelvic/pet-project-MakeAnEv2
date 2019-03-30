package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.UserGroup;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbQuery {

    public static User findUser(Connection con, String username, String password) throws SQLException {
        String sql = "SELECT id_user, username, password, email, phone, name, surname, comment FROM user "
                + "WHERE username = ? and password = ?";

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

    public static User findUser(Connection con, String username) throws SQLException {
        String sql = "SELECT id_user, username, password, email, phone, name, surname, comment FROM user "
                + "WHERE username = ?";

        PreparedStatement pstm = con.prepareStatement(sql);

        pstm.setString(1, username);

        ResultSet rs = pstm.executeQuery();

        if (rs.next()) {
            return new User(rs);
        }
        return null;
    }

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

//    public static String selectUserGroup(Connection con, int group_id) throws SQLException {
//        String sql = "SELECT Name FROM usergroups WHERE id_group = ?";
//
//        PreparedStatement pstm = con.prepareStatement(sql);
//        pstm.setInt(1, group_id);
//
//        ResultSet rs = pstm.executeQuery();
//        rs.next();
//        return rs.getString(1);
//    }

    public static void insertUser(Connection con, User user) throws SQLException {
        /*метод для добавления записи в таблиц пользователей, передаем соединение и пользователя, метод записывает данные в таблицу по соединению*/

        String sql = "INSERT INTO user(group_id, username,  password, email, phone, name, surname, comment) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, user.getGroup_id());
        ps.setString(2, user.getUsername());
        ps.setInt(3, user.getPassword());
        ps.setString(4, user.getEmail());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getName());
        ps.setString(7, user.getSurname());
        ps.setString(8, user.getComment());

        ps.executeUpdate();
    }

    public static List<User> selectUser(Connection con) throws SQLException {
        /*метод для получения атрибутов пользователя из БД*/
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

    public static User selectUser(Connection con, String id_userStr) throws SQLException {
        /*перегруженный метод, возвращающий одного пользователя по id*/
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

    public static void updateUser(Connection con, User user) throws SQLException {
        /*обновляем информацию о пользователе в БД*/
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

    public static void updateUserPassword(Connection con, User user) throws SQLException {
        /*обновляем пароль для пользователя*/
        String sql = "UPDATE user SET password = ? WHERE id_user = ?";

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, user.getPassword());
        ptsm.setInt(2, user.getId_user());

        ptsm.executeUpdate();

    }

    public static void updateUserPassword(Connection con, String id_user) throws SQLException {
        /*обновляем пароль для пользователя*/
        String sql = "UPDATE user SET password = ? WHERE id_user = ?";
        String pass = "0";
        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, pass.hashCode());
        ptsm.setString(2, id_user);

        ptsm.executeUpdate();

    }

    public static void deleteUser(Connection con, String id_user) throws SQLException, NumberFormatException {
        /*Удаляем запись пользователя*/
        String sql = "DELETE FROM user WHERE id_user=?";

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, Integer.parseInt(id_user));

        ptsm.executeUpdate();
    }

}
