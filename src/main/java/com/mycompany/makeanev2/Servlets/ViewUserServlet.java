package com.mycompany.makeanev2.Servlets;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import com.mycompany.makeanev2.Utils.DbQuery;
import com.mycompany.makeanev2.Utils.DbConnection;
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
        User userInSession; //сюда помещаем залогинившегося пользователя

        try {
            Connection con = DbConnection.getConnection();
            
            //определяем запрос на пользователя
            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            user = DbQuery.selectUser(con, id_user); //может передавать NumberFormatException поскольку в методе есть ParsInt
            con.close();
            
            //проверим может ли авторизованный пользователь получить доступ к запрашиваемому пользователю
            userInSession = AuthUtils.getLoginedUser(request.getSession());
            CheckPermission.checkViewUserAcces(userInSession, user);
            
            //отображаем страницу
            request.setAttribute("user", user); //передаем объект на страницу для настройки view
            String redirect = (String) request.getSession().getAttribute("redirRole"); //смотрим куда надо идт в зависимости от роли
            request.getRequestDispatcher(redirect).forward(request, response); //открываем страницу viewuser
            
        } catch (SQLException | NamingException | NumberFormatException | UserException ex) {
            
            errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
        
    }
}
