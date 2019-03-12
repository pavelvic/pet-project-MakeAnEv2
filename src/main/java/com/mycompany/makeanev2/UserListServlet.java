package com.mycompany.makeanev2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); //на всякий случай ставим правильную кодировку

        String errorString; // строка с возможными ошибками
        List<User> list; //сюда помещаем результат запроса

        try {
            Connection con = DbConnection.getConnection();
            list = DbQuery.selectUser(con);
            con.close(); //закрываем соединение сразу после получения данных
            request.setAttribute("userList", list); //передаем объект на страницу для настройки view
            request.getRequestDispatcher("/userlist.jsp").forward(request, response); //открываем страницу view
        } catch (SQLException | NamingException ex) {
            errorString = "Ошибка соединения с базой данных! "+ex.getMessage(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на главную
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }

}
