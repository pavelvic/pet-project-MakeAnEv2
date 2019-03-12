package com.mycompany.makeanev2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserServlet extends HttpServlet {

    private String resultString = null;
    private User user;
    private String id_user;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Connection con = DbConnection.getConnection();

            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            user = DbQuery.selectUser(con, id_user);
            con.close(); //закрываем соединение сразу после получения данных
            request.setAttribute("user", user); //передаем объект на страницу для настройки view

            request.getRequestDispatcher("/edituser.jsp").forward(request, response);
        } catch (SQLException | NamingException | NumberFormatException ex) {
            resultString = "Ошибка! " + ex.getMessage(); //информация об ошибке
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            Connection con = DbConnection.getConnection();

            id_user = (String) request.getParameter("id_user"); //параметры только строки
            String username = (String) request.getParameter("username");
            String email = (String) request.getParameter("email");
            String phone = (String) request.getParameter("phone");
            String name = (String) request.getParameter("name");
            String surname = (String) request.getParameter("surname");
            String comment = (String) request.getParameter("comment");

            if (("".equals(username)) | ("".equals(email))) {
                resultString = "Пользователь не отредактирован! Не заполнены обязательные* поля!";
            }

            if (resultString == null) {
                User user = new User(Integer.parseInt(id_user), username,null, email, phone, name, surname,comment);
                DbQuery.updateUser(con, user);
                con.close();
                resultString = "Изменения внесены";
            }
        } catch (SQLException | NamingException | NumberFormatException ex) {
            resultString = "Ошибка ! " + ex.getMessage();
        } finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/edituser?id_user=" + id_user); //указываем откуда мы идем на страницу результата для настройки маршрутизации

            request.getRequestDispatcher("/resultpage.jsp").forward(request, response);
        }
    }
}
