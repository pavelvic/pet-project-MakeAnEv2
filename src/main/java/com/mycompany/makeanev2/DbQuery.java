package com.mycompany.makeanev2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbQuery {

    public static void insertUser(Connection con, User user) throws SQLException {
        /*метод для добавления записи в таблиц пользователей, передаем соединение и пользователя, метод записывает данные в таблицу по соединению*/

        String sql = "INSERT INTO user(username, password, email, phone, name, surname, comment) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1, user.getUsername());
        ps.setInt(2, user.getPassword());
        ps.setString(3, user.getEmail());
        ps.setString(4, user.getPhone());
        ps.setString(5, user.getName());
        ps.setString(6, user.getSurname());
        ps.setString(7, user.getComment());

        ps.executeUpdate();
    }

    public static List<User> selectUser(Connection con) throws SQLException {
        /*метод для получения атрибутов пользователя из БД*/
        String sql = "SELECT id_user, username, email, phone, name, surname, comment FROM user";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();
        List<User> list = new ArrayList<>();

        while (rs.next()) {
            //раскладываем значения из запроса по переменным
            int id_user = rs.getInt("id_user");
            String username = rs.getString("username");
            String email = rs.getString("email");
            String phone = rs.getString("phone");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String comment = rs.getString("comment");
            

            //создаем экземпляр User с выбранными из БД параметрами
            
            User user = new User(id_user, username, email, phone, name, surname, comment);

            //добавляем объект в List для будущего использования на странице вывода списка пользователей
            list.add(user);
        }
        return list;
    }

    public static User selectUser(Connection con, String id_userStr) throws SQLException {
        /*перегруженный метод, возвращающий одного пользователя по id*/
        String sql = "SELECT id_user, username, email, phone, name, surname, comment FROM user WHERE id_user = ?";
        
        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setString(1, id_userStr);

        ResultSet rs = ptsm.executeQuery();
        rs.next(); //помещаем курсор ResultSet на первую строку для разбора по переменным
        
        //делаем преобразование в int для id_user
        int id_user = Integer.parseInt(id_userStr);
        
        //раскладываем значения из запроса по переменным
        String username = rs.getString("username");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String comment = rs.getString("comment");

        //создаем экземпляр User с выбранными из БД параметрами
        User user = new User(id_user, username, email, phone, name, surname, comment);
        return user;
    }


    public static void updateUser(Connection con, User user) throws SQLException {
        /*обновляем информацию о пользователе в БД*/
        String sql = "UPDATE user SET username=?, email=?, phone=?, name=?, surname=?, comment=? WHERE id_user =?"; //? - параметр, подставляем из экземпляра

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setString(1, user.getUsername()); //первый параметр в SQL-запросе (?)
        ptsm.setString(2, user.getEmail()); //второй и так далее
        ptsm.setString(3, user.getPhone());
        ptsm.setString(4, user.getName());
        ptsm.setString(5, user.getSurname());
        ptsm.setString(6, user.getComment());
        ptsm.setInt(7, user.getId_user());

        ptsm.executeUpdate();
    }
    
    public static void deleteUser (Connection con, String id_user) throws SQLException, NumberFormatException {
        /*Удаляем запись пользователя*/
        String sql = "DELETE FROM user WHERE id_user=?";
        
        PreparedStatement ptsm = con.prepareStatement(sql);
        
        ptsm.setInt(1, Integer.parseInt(id_user));
        
        ptsm.executeUpdate();
    }

}
