package com.mycompany.makeanev2.Servlets.Event;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Exceptions.ParticipantException;
import com.mycompany.makeanev2.Participant;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*обработка вывода списка пользователей (все пользователи в БД)
URL ???? */
public class UnsubscribeEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ZoneId timeZone = (ZoneId) request.getServletContext().getAttribute("ZoneId");
        String resultString = null;
        Participant unsubscriber;

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User userInSession = AuthUtils.getLoginedUser(session);

        try {
            //получаем мероприятие к которому хотим получить доступ

            String id_event = (String) request.getParameter("id_event"); //параметры Object, приводим к String

            Connection con = DbConnection.getConnection();
            Event event = EventDbQuery.selectEvent(con, Integer.parseInt(id_event));
            List<Participant> participants = EventDbQuery.selectParticipantsOfEvent(con, event);

            event.setZone(timeZone);
            for (Participant participant : participants) {
                participant.setZone(timeZone);
            }

            event.setParticipants(participants);

            unsubscriber = event.getParticipantByPerson(userInSession);

            //проверка на авторство с генерацией исключения
            if (unsubscriber.getPerson().getId_user() == event.findAuthor().getPerson().getId_user()) {
                throw new ParticipantException("Невозможно отказаться от участия инициатору (автору) события");
            }

            //получить участника мероприятия из event для удаления (на основе пользователя в сессии)
            //в метод getParticipant объекта event передать userInSession
            //в get-методе устроить перебор, сравнить person и userInSession через equals и вернуть первое совпадение в виде PArticipant
            //если такого нет - выбросить исключение ParticipantException
            //далее написать метод с sql-запросом, на входе PArticipant, в этом методе разбираются параметры для sql-Запроса (id_ события, id_уч и проч)
            //sql-запрос удаляет запись из таблицы Participants
            EventDbQuery.deleteParticipant(con, unsubscriber);
            con.close();
            resultString = "Вы удалены из участников события №" + event.getId_event() + " [" + event.getName() + "]";

            //если что-то пошло не так    
        } catch (SQLException | NamingException | NumberFormatException | ParticipantException | EventException ex) {
            resultString = "Ошибка! " + ex.toString(); //информация об ошибке

        } finally {

            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/viewevent?id_event=" + request.getParameter("id_event")); //указываем чтобы маршрутизация с resultpage была на это же событие
            //request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой          
            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
