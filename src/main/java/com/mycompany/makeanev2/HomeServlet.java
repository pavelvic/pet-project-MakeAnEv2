package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
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

    private List<Event> futureEvents;
    private List<User> authors;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errorString;
        ZoneId timeZone = (ZoneId) request.getServletContext().getAttribute("ZoneId");

        try {
            Connection con = DbConnection.getConnection();
            authors = EventDbQuery.selectAllAuthors(con);
            futureEvents = EventDbQuery.selectEventsFromDateExceptStatus(con, ZonedDateTime.now(ZoneId.of("UTC")), new EventStatus(3, "Отменено"));
            //добавить списко авторов

            con.close();
            
            //authors.add(new User(0, 0, "", "", 0, "", "", "", "", "", ""));

            if (futureEvents != null) {
                for (Event event : futureEvents) {
                    event.setZone(timeZone);

                }
            }
            request.setAttribute("events", futureEvents);
            request.setAttribute("authors", authors);
            //добавить в аттрибут

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString = "Что-то пошло не так...";
        List<Event> searchedEvents;
        int author_id = 0;

        try {
            String dateFromStr = (String) request.getParameter("dateFrom");
            request.setAttribute("dateFrom", dateFromStr);
            LocalDate dateFrom = null;
            LocalDateTime dateTimeFrom = null;

            String dateToStr = (String) request.getParameter("dateTo");
            request.setAttribute("dateTo", dateToStr);
            LocalDate dateTo = null;
            LocalDateTime dateTimeTo = null;

            String searchDesc = (String) request.getParameter("searchDesc").trim();
            request.setAttribute("searchDesc", searchDesc);

            String author = (String) request.getParameter("author");
            String splitauthor[];
            String author_idStr = null;
            String author_name = null;

            if (author.compareTo(":") != 0) {
                splitauthor = author.split(":");
                author_idStr = splitauthor[0];
                author_id = Integer.parseInt(author_idStr);
                author_name = splitauthor[1];
            }
            request.setAttribute("author_id", author_idStr);
            request.setAttribute("author_name", author_name);

            //проверки атрибутов серчбокса
            if (!(dateFromStr.isEmpty() & dateToStr.isEmpty())) {
                if ((!dateFromStr.isEmpty() & dateToStr.isEmpty()) | (dateFromStr.isEmpty() & !dateToStr.isEmpty())) {
                    throw new Exception("Должны быть заполнены оба поля: 'c' и 'по'");
                } else {
                    dateFrom = LocalDate.parse(dateFromStr);
                    dateTimeFrom = dateFrom.atTime(0, 0);
                    dateTo = LocalDate.parse(dateToStr);
                    dateTimeTo = dateTo.atTime(23, 59);
                    if (dateFrom.isAfter(dateTo)) {
                        throw new Exception("Дата 'с' не может быть позже даты 'по'");
                    }
                }
            }

            //ЗАПРОС С ПОИСКОМ СДЕСЬ!
            Connection con = DbConnection.getConnection();
            searchedEvents = EventDbQuery.selectEventsByParam(con, 
                    dateTimeFrom, 
                    dateTimeTo, 
                    author_id, 
                    searchDesc); //sql-запрос
            con.close();
            
            resultString = "Найдено "+ searchedEvents.size() +" записей"; //TODO: специфицировать

            request.setAttribute("events", searchedEvents);
        } catch (Exception ex) {
            resultString = "Ошибка! " + ex.getMessage();
            //request.setAttribute("events", futureEvents);

        } finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("authors", authors);
            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу
        }
    }
}
