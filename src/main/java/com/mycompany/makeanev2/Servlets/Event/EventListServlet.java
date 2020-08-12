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

/*обработка вывода списка пользователей (все пользователи в БД)
URL /userlist */
public class EventListServlet extends HttpServlet {
    //контейнер-коллекция для хранения списка созданных пользователем событий

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Event> likeAuthorEvents = null;
        List<Event> likeParticipantEvents = null;  //контейнер-коллекция для хранения списка событий, где пользователь НЕ автор, НО участник
        String errorString; // строка с возможными ошибками
        //устанавливаем временную зону, в которую пересчитываем даты
//    ZoneId timeZone = ZoneId.of("Europe/Moscow"); 
        ZoneId timeZone = (ZoneId) request.getServletContext().getAttribute("ZoneId");
        try {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            User userInSession = AuthUtils.getLoginedUser(session);

            //формируем списки событий в зависимости от группы пользователей (для разных ролей вывод разных списков)
            Connection con = DbConnection.getConnection();
            switch (userInSession.getGroup_id()) {
                case 1: //супер пользователь может быть автором и участником
                    likeAuthorEvents = EventDbQuery.selectEventsByUserLikeAuthor(con, userInSession.getId_user());
                    likeParticipantEvents = EventDbQuery.selectEventsByUserLikeParticipant(con, userInSession.getId_user());
                    break;
                case 2: //админ - автор и участник
                    likeAuthorEvents = EventDbQuery.selectEventsByUserLikeAuthor(con, userInSession.getId_user());
                    likeParticipantEvents = EventDbQuery.selectEventsByUserLikeParticipant(con, userInSession.getId_user());
                    break;
                case 3: //менеджер только участник
                    likeParticipantEvents = EventDbQuery.selectEventsByUserLikeParticipant(con, userInSession.getId_user());
                    break;
                case 4: //пользователь только участник
                    likeParticipantEvents = EventDbQuery.selectEventsByUserLikeParticipant(con, userInSession.getId_user());
                    break;
            }
            con.close();

            //применяем правильную временную зону для вывода дат на страницу
            if (likeAuthorEvents != null) {
                for (Event likeAuthorEvent : likeAuthorEvents) {
                    likeAuthorEvent.setZone(timeZone);
                }
            }

            if (likeAuthorEvents != null) {
                for (Event likeParticipantEvent : likeParticipantEvents) {
                    likeParticipantEvent.setZone(timeZone);
                    for (Participant plist : likeParticipantEvent.getParticipants()) {
                        plist.setZone(timeZone);
                    }
                }
            }

            request.setAttribute("likeAuthorEvents", likeAuthorEvents); //передаем коллекцию на страницу для отображения
            request.setAttribute("likeParticipantEvents", likeParticipantEvents);

            //берем из http-запроса инфу какую страницу открывать (определяется с фильтре в зависимости от полномочий)
            //для каждой группы пользователей есть своя страница со списком пользователей для обеспечения возможностей кастомизации
            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу

            //если что-то пошло не так    
        } catch (SQLException | NamingException | EventException ex) {
            errorString = "Ошибка соединения с базой данных! " + ex.getMessage(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на главную
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO: Логика поиска и вывода по кнопке
        //один из вариантов реализации поиска
        //получить список для поиска
        //получить что искать (строку)
        //пройти все записи списка и где такая строка находится, оставить, остальные - удалить из list
        //передать на страницу новый список и отобразить
        //подсветить искомую подстроку - опиционально   
    }
}
