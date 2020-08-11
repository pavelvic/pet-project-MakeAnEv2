package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Exceptions.SearchException;
import com.mycompany.makeanev2.Utils.CalendarUtils;
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
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация события открытия главной страницы, URL '/'  */
public class HomeServlet extends HttpServlet {

    private List<Event> events;
    private List<Participant> authors;
    private List<EventStatus> statuses;

    private List<WeekOfMonth> weeksPrevMonth;
    private List<WeekOfMonth> weeksActualMonth;
    private List<WeekOfMonth> weeksNextMonth;
    private final Locale loc = Locale.forLanguageTag("ru");

    LocalDate prevDate;
    LocalDate actualDate;
    LocalDate nextDate;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errorString;
        ZoneId timeZone = (ZoneId) request.getServletContext().getAttribute("ZoneId");

        try {

            String dayStr = (String) request.getParameter("day"); //параметры Object, приводим к String
            String monthStr = (String) request.getParameter("month"); //параметры Object, приводим к String
            String yearStr = (String) request.getParameter("year"); //параметры Object, приводим к String

            if (dayStr != null & monthStr != null & yearStr != null) {
                int day = Integer.parseInt(dayStr);
                int month = Integer.parseInt(monthStr);
                int year = Integer.parseInt(yearStr);

                LocalDateTime dateFrom = LocalDateTime.of(year, Month.of(month), day, 0, 0);
                LocalDateTime dateTo = LocalDateTime.of(year, Month.of(month), day, 23, 59);

                Connection con = DbConnection.getConnection();
                authors = EventDbQuery.selectAllAuthors(con);
                statuses = EventDbQuery.selecAllStatuses(con);
                //events = EventDbQuery.selectEventsByParam(con, dateFrom, dateTo, 0, "", new ArrayList<EventStatus>());
                events = EventDbQuery.selectEventsByParam(con, dateFrom, dateTo, 0, "", null);
                con.close();

                request.setAttribute("dateFrom", LocalDate.of(year, Month.of(month), day));
                request.setAttribute("dateTo", LocalDate.of(year, Month.of(month), day));
            } else {
                Connection con = DbConnection.getConnection();
                authors = EventDbQuery.selectAllAuthors(con);
                statuses = EventDbQuery.selecAllStatuses(con);
                events = EventDbQuery.selectEventsFromDateExceptStatus(con, ZonedDateTime.now(ZoneId.of("UTC")), new EventStatus(3, "Отменено"));
                con.close();
            }

            if (events != null) {
                for (Event event : events) {
                    event.setZone(timeZone);
                }
            }
            request.setAttribute("events", events);
            request.setAttribute("authors", authors);
            request.setAttribute("statuses", statuses);
            request.setAttribute("resultString", "Найдено cобытий: "+events.size());

            //генерируем календарь на прошлый, текущий и будущий месяц для вывода на странице
            prevDate = LocalDate.now(timeZone).minusMonths(1);
            weeksPrevMonth = CalendarUtils.getWeeksOfMonth(prevDate.getYear(), prevDate.getMonth());

            actualDate = LocalDate.now(timeZone);
            weeksActualMonth = CalendarUtils.getWeeksOfMonth(actualDate.getYear(), actualDate.getMonth());

            nextDate = LocalDate.now(timeZone).plusMonths(1);
            weeksNextMonth = CalendarUtils.getWeeksOfMonth(nextDate.getYear(), nextDate.getMonth());

            request.setAttribute("yearPrevMonth", prevDate.getYear());
            request.setAttribute("namePrevMonth", prevDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, loc));
            request.setAttribute("weeksPrevMonth", weeksPrevMonth);

            request.setAttribute("yearActualMonth", actualDate.getYear());
            request.setAttribute("nameActualMonth", actualDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, loc));
            request.setAttribute("weeksActualMonth", weeksActualMonth);

            request.setAttribute("yearNextMonth", nextDate.getYear());
            request.setAttribute("nameNextMonth", nextDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, loc));
            request.setAttribute("weeksNextMonth", weeksNextMonth);

            //открываем главную страницу
            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу

        } catch (SearchException | SQLException | NamingException | EventException ex) {
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

            String[] statusesStr = request.getParameterValues("statuses");
            for (EventStatus status : statuses) {
                status.setNotChecked();

                if (statusesStr != null) {
                    for (int i = 0; i < statusesStr.length; i++) {
                        if (Integer.parseInt(statusesStr[i]) == status.getId_eventStatus()) {
                            status.setChecked();
                        }
                    }
                }
            }

            List<EventStatus> selectedStatuses = new ArrayList<>();

            for (EventStatus status : statuses) {
                if (status.getChecked().compareTo("checked") == 0) {
                    selectedStatuses.add(status);
                }
            }

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
                    throw new SearchException("Должны быть заполнены оба поля: 'c' и 'по'");
                } else {
                    dateFrom = LocalDate.parse(dateFromStr);
                    dateTimeFrom = dateFrom.atTime(0, 0);
                    dateTo = LocalDate.parse(dateToStr);
                    dateTimeTo = dateTo.atTime(23, 59);
                    if (dateFrom.isAfter(dateTo)) {
                        throw new SearchException("Дата 'с' не может быть позже даты 'по'");
                    }
                }
            }

            //ЗАПРОС С ПОИСКОМ
            Connection con = DbConnection.getConnection();
            searchedEvents = EventDbQuery.selectEventsByParam(con,
                    dateTimeFrom,
                    dateTimeTo,
                    author_id,
                    searchDesc,
                    selectedStatuses); //sql-запрос
            con.close();

            resultString = "Найдено " + searchedEvents.size() + " записей"; //TODO: специфицировать

            request.setAttribute("events", searchedEvents);
        } catch (SearchException | SQLException | NamingException | EventException ex) {
            resultString = "Ошибка! " + ex.toString();
            //request.setAttribute("events", futureEvents);

        } finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("authors", authors);
            request.setAttribute("statuses", statuses);
            request.setAttribute("yearPrevMonth", prevDate.getYear());
            request.setAttribute("namePrevMonth", prevDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, loc));
            request.setAttribute("weeksPrevMonth", weeksPrevMonth);

            request.setAttribute("yearActualMonth", actualDate.getYear());
            request.setAttribute("nameActualMonth", actualDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, loc));
            request.setAttribute("weeksActualMonth", weeksActualMonth);

            request.setAttribute("yearNextMonth", nextDate.getYear());
            request.setAttribute("nameNextMonth", nextDate.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, loc));
            request.setAttribute("weeksNextMonth", weeksNextMonth);

            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу
        }
    }
}
