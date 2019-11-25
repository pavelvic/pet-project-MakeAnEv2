package com.mycompany.makeanev2.Servlets.Event;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Exceptions.ParticipantException;
import com.mycompany.makeanev2.Participant;
import com.mycompany.makeanev2.ParticipantStatus;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
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
import javax.servlet.http.HttpSession;

/*обработка вывода списка пользователей (все пользователи в БД)
URL ???? */
public class SubscribeEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ZoneId timeZone = (ZoneId) request.getServletContext().getAttribute("ZoneId");
        String resultString = null;


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
            Participant author = event.findAuthor();


            //проверим доступные статусы события: в уже запланированные (статус события "Запланировано") и проведенные ("Проведено") события нельзя региться
            //также нельзя региться в события, регистрация в которые закрыта (статус регистрации "Закрыт")
            event.checkEventStatus();
            event.checkEventRegStatus();

            //формирруем самого уастника стандартным способом (на основании userInSession)
            //два шага: 1. статус участника: если в событии уже больше допустимых уастников, то запасной, если меньше - основной
            //2. сам участник стандартным конструктором
            ParticipantStatus ps = new ParticipantStatus();
            if (participants.size() >= event.getMaxParticipants()) {
                ps.setReserveStatus();
            }

            Participant participant = new Participant(event, userInSession, ps, userInSession, ZonedDateTime.now(ZoneId.of("UTC")));

            //проверяем зареген ли уже на событии участник, если да - выдаем исключение Участника об этом

                for (Participant pc : participants) {
                    if (pc.getPerson().getId_user() == participant.getPerson().getId_user()) {
                        throw new ParticipantException("Вы уже участвуете в мероприятии");
                    }
                }


            //добавляем участника в событие стандартным запросом, если все проверки пройдены и мы не получили исключений
            EventDbQuery.insertParticipant(con, participant);
            con.close();
            resultString = "Вы добавлены. Количество мест ограничено (" + event.getMaxParticipants() + "). Ваше место " + (participants.size() + 1)
                    + ". Статус: " + participant.getStatus().getName();

            //если что-то пошло не так    
        } catch (SQLException | NamingException | NumberFormatException | EventException | ParticipantException ex) {
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
