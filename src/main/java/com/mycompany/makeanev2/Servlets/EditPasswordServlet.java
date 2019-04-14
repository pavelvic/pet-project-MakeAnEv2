package com.mycompany.makeanev2.Servlets;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.Utils.AuthUtils;
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

public class EditPasswordServlet extends HttpServlet {
//private RequestDispatcher dispatcher;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
//        dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
//        dispatcher.forward(request, response); //открываем страницу 
        
        request.getRequestDispatcher("/WEB-INF/editpass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString = "";

        String id_user = (String) request.getParameter("id_user");
        User userInSession = AuthUtils.getLoginedUser(request.getSession());

        try {
            

            //получаем пароль для сравнения
            String actualPassword = (String) request.getParameter("actualPassword");
            String newPassword = (String) request.getParameter("newPassword");

            User user = (User) request.getAttribute("user");
            
            //обработка нужна для нормальной работы суперпользователя, он не доджен вводить пароль пользователя дла смены
            int actualHashCodePassword=-1;
            if (actualPassword != null) {
                actualHashCodePassword = actualPassword.hashCode();
            }
            

            if ((actualHashCodePassword == user.getPassword())||(userInSession.getGroup_id()==1)) {
                user.setPassword(newPassword);
                user.checkPasswordPattern();
                
                Connection con = DbConnection.getConnection();
                DbQuery.updateUserPassword(con, user);
                con.close();
                resultString = "Пароль изменён";
            } else {
                resultString = "Пароль не совпадает. Введите правильный пароль";

            }

        } catch (SQLException | NamingException | UserException ex) {
            resultString = "Ошибка! " + ex.toString();
        } finally {
            request.setAttribute("resultString", resultString);

            //request.setAttribute("id_user", id_user);
            request.getRequestDispatcher("/WEB-INF/editpass.jsp").forward(request, response);
            //dispatcher.forward(request, response);
        }

    }
}
