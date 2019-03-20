package com.mycompany.makeanev2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString = null;
        String id_user = (String) request.getParameter("id_user"); //параметры только строки
        
        try {
            Connection con = DbConnection.getConnection();
            
            DbQuery.updateUserPassword(con, id_user);
            con.close();
            resultString = "Пароль сброшен. Для получения нового обратитесь к администартору"; //специфицировать резалт
        } catch (SQLException | NamingException  ex) {
            resultString = "Ошибка! "+ex.getMessage();
        }
        
        finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/viewuser?id_user="+id_user); //указываем откуда мы идем на страницу результата для настройки маршрутизации
            
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response);
        }
    }
}
