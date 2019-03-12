package com.mycompany.makeanev2;

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
        
        try {
            Connection con = DbConnection.getConnection();
            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            DbQuery.deleteUser(con, id_user);
            con.close();
            resultString = "Пользователь удалён"; //специфицировать резалт
        } catch (SQLException | NamingException | NumberFormatException ex) {
            resultString = "Ошибка! "+ex.getMessage();
        }
        
        finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/userlist"); //указываем откуда мы идем на страницу результата для настройки маршрутизации
            
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response);
        }
    }
}
