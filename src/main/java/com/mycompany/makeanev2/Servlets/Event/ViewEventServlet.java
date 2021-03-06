package com.mycompany.makeanev2.Servlets.Event;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Exceptions.EventException;
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

/*реализация просмотра информации о мероприятии
URL /viewevent?id_event = ?*/
public class ViewEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        ZoneId timeZone = ZoneId.of("Europe/Moscow");
        ZoneId timeZone = (ZoneId) request.getServletContext().getAttribute("ZoneId");
        String errorString;
        boolean regFlag = true;

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User userInSession = AuthUtils.getLoginedUser(session);

        try {
            //получаем мероприятие к которому хотим получить доступ
            Connection con = DbConnection.getConnection();
            String id_event = (String) request.getParameter("id_event"); //параметры Object, приводим к String

            Event event = EventDbQuery.selectEvent(con, Integer.parseInt(id_event));
            List<Participant> participants = EventDbQuery.selectParticipantsOfEvent(con, event);
//            Participant author = EventDbQuery.selectAuthorOfEvent(con, event);
            con.close();

            event.setZone(timeZone);
            //author.setZone(timeZone);
            if (participants != null) {
                for (Participant participant : participants) {
                    participant.setZone(timeZone);
                    if (participant.getPerson().getId_user() == userInSession.getId_user()) {
                        regFlag = false;
                    }
                }
            }

            event.setParticipants(participants);
            Participant author = event.findAuthor();

            request.setAttribute("event", event);
            request.setAttribute("regFlag", regFlag); //для организации отображения кнопки "Участвую" / "Отказаться", в зависимости от нахождения в списке участников
            request.setAttribute("author", author);
            request.setAttribute("participants", participants);

            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу

            //если что-то пошло не так    
        } catch (SQLException | NamingException | NumberFormatException | EventException ex) {
            errorString = "Ошибка! " + ex.getMessage(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на главную
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }

}
