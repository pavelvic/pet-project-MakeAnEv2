package com.mycompany.makeanev2.Servlets.Event;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String resultString = null;
        Event event = (Event) request.getAttribute("event");
        

        try {
            Connection con = DbConnection.getConnection();
            con.setAutoCommit(false); 
            EventDbQuery.deleteParticipantsOfEvent(con, event);
            EventDbQuery.deleteEvent(con, event); //запрос в БД для удаления 
            con.commit();
            //con.setAutoCommit(true);
            con.close();
            resultString = "Мероприятие удалёно"; //TODO: Специфицировать

            //если что-то пошло не так, сообщаем иной результат операции с текстом ошибки
        } catch (SQLException | NamingException | NumberFormatException | NullPointerException ex) {
            resultString = "Ошибка! " + ex.getMessage();
        } //вывод результата операции 
        finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/events"); //указываем откуда мы идем на страницу результата для настройки маршрутизации
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
        }
    }
}