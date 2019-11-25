package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация события открытия главной страницы, URL '/'  */
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errorString;
        ZoneId timeZone = (ZoneId) request.getServletContext().getAttribute("ZoneId");

        try {
            Connection con = DbConnection.getConnection();
            List<Event> allEvents = EventDbQuery.selectEventsFromDateExceptStatus(con, ZonedDateTime.now(ZoneId.of("UTC")), new EventStatus(3, "Отменено"));
            //добавить списко авторов
            List <Participant> authors = null;
            con.close();

            if (allEvents != null) {
                for (Event event : allEvents) {
                    event.setZone(timeZone);
                    
                }
            }
            request.setAttribute("allEvents", allEvents);
            //добавить в аттрибут
            request.setAttribute("authors", authors);
            
            //открываем главную страницу
            //request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу

        } catch (SQLException | NamingException ex) {
            errorString = "Ошибка соединения с базой данных! " + ex.getMessage(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на главную
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }
}
