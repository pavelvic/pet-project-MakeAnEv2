package com.mycompany.makeanev2.Servlets;

import com.mycompany.makeanev2.User;
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

public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString = null;
        
        User userToDelete = (User)request.getAttribute("user");
        
        try {
            Connection con = DbConnection.getConnection();
            DbQuery.deleteUser(con, userToDelete);
            con.close();
            resultString = "Пользователь удалён"; //специфицировать резалт
        } catch (SQLException | NamingException | NumberFormatException ex) {
            resultString = "Ошибка! "+ex.getMessage();
        }
        
        finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/"); //указываем откуда мы идем на страницу результата для настройки маршрутизации
            
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
        }
    }
}
