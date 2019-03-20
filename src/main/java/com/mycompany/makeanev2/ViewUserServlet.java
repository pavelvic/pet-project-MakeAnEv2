package com.mycompany.makeanev2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String errorString; // строка с возможными ошибками
        User user; //сюда помещаем результат запроса

        try {
            Connection con = DbConnection.getConnection();
            
            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            user = DbQuery.selectUser(con, id_user); //может передавать NumberFormatException поскольку в методе есть ParsInt
            con.close(); 
            request.setAttribute("user", user); //передаем объект на страницу для настройки view
            request.getRequestDispatcher("/viewuser.jsp").forward(request, response); //открываем страницу view
        } catch (SQLException | NamingException | NumberFormatException ex) {
            //TODO: специфицировать проверку на наличие пользователя?
            errorString = "Ошибка! " + ex.getMessage(); //информация об ошибке
                        
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
        
    }
}
