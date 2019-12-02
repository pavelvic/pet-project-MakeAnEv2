package com.mycompany.makeanev2.Servlets.Event;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.EventRegStatus;
import com.mycompany.makeanev2.EventStatus;
import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Participant;
import com.mycompany.makeanev2.ParticipantStatus;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*реализация создания меропприятия
URL /createev*/
public class CreateEventServlet extends HttpServlet {

    RequestDispatcher dispatcher;
    Connection con;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
        dispatcher.forward(request, response); //открываем страницу
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultString = null; //переменная для фиксации результата операции
        Event event = null; //регистрируемое мероприятие пользователь для записи в БД

        try {
            //разбираем введенные в форму регистрации параметры из http-запроса
            String name = (String) request.getParameter("name");
            String description = (String) request.getParameter("description");
            String id_eventregstatus = (String) request.getParameter("id_eventregstatus");
            String place = (String) request.getParameter("place");
            String parEventTime = (String) request.getParameter("eventTime");
            String maxParticipants = (String) request.getParameter("maxParticipants");
            String parCritTime = (String) request.getParameter("critTime");

//            LocalDateTime eventTime = LocalDateTime.parse(parEventTime);
//            LocalDateTime critTime = LocalDateTime.parse(parCritTime);
            LocalDateTime eventLocalTime = LocalDateTime.parse(parEventTime);
            ZonedDateTime eventZonedTime = ZonedDateTime.of(eventLocalTime, ZoneId.of("UTC"));

            LocalDateTime critLocalTime = LocalDateTime.parse(parCritTime);
            ZonedDateTime critZonedTime = ZonedDateTime.of(critLocalTime, ZoneId.of("UTC"));

            con = DbConnection.getConnection();
            EventRegStatus evRegSt = EventDbQuery.selectEventRegStatusById(con, Integer.parseInt(id_eventregstatus)); //получаем выбранный на странице статус из БД

            int id_event = EventDbQuery.selectMaxEventId(con) + 1;
            //создаем событие
            event = new Event(id_event,
                    new EventStatus(),
                    evRegSt,
                    name,
                    description,
                    place,
                    eventZonedTime,
                    Integer.parseInt(maxParticipants),
                    ZonedDateTime.now(ZoneId.of("UTC")),
                    critZonedTime,
                    ZoneId.of("UTC"));

            //проверяем правильно ли введены даты события (дата и время должна быть позже критичной даты)
            event.checkTimes();

            //добавляем первого участника мероприятия
            //определяем от имени кого создается мероприятие (залогинившейся) - он и есть первый участник и автор
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            User userInSession = AuthUtils.getLoginedUser(session);

            //формируем объект Participant: участник - текущий пользователь, он же добавил событие
            //Participant author = new Participant(event, userInSession, new ParticipantStatus(), userInSession, ZonedDateTime.now(ZoneId.of("UTC")));
            Participant author = new Participant(userInSession, new ParticipantStatus(), userInSession, ZonedDateTime.now(ZoneId.of("UTC")));
            author.setAsAuthor(); //устанавливаем признак авторства для первого участника

            //пишем его в БД (транзакционно с созданием записи мероприятия)
            //добавляем запись в БД и устанавливаем сообщение об успехе
            con.setAutoCommit(false);
            EventDbQuery.insertEvent(con, event);
            EventDbQuery.insertParticipant(con, author, event);
            con.commit();
            con.close();

            resultString = "Мероприятие " + "№" + event.getId_event() + " [" + event.getName() + "], создано!";

            event = null; //обнуляем экземпляр, чтобы на старнице вывелись пустые поля

            //если что-то пошло не так, фиксируем ошибку
        } catch (SQLException | NamingException | EventException | NumberFormatException | NullPointerException ex) {
            try {
                con.close();
            } catch (SQLException ex1) {
                resultString = "Ошибка! " + ex1.toString();
            }
            resultString = "Ошибка! " + ex.toString();

            //выводим результат операции
        } finally {

            request.setAttribute("resultString", resultString);
            request.setAttribute("event", event);
            dispatcher.forward(request, response); //идем на страницу в завиимости от dispatcher
        }
    }
}
