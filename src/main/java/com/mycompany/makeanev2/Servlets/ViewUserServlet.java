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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User)request.getAttribute("user");
        RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
        dispatcher.forward(request, response); //открываем страницу 
        //переходим на нужную страницу

//        
//        String errorString; // строка с возможными ошибками
//        User user; //сюда помещаем результат запроса
//        User userInSession; //сюда помещаем залогинившегося пользователя
//
//        try {
//            
//            
//
//            //отображаем страницу
//            request.setAttribute("user", user); //передаем объект на страницу для настройки view
//            String redirect = (String) request.getAttribute("redirUsergroup"); //смотрим куда надо идт в зависимости от роли
//            request.getRequestDispatcher(redirect).forward(request, response); //открываем страницу viewuser
//            
//        } catch (SQLException | NamingException | NumberFormatException | UserException ex) {
//            
//            errorString = "Ошибка! " + ex.toString(); //информация об ошибке
//            request.setAttribute("resultString", errorString);
//            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
//            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
//        }
        
    }
}
