package com.mycompany.makeanev2.Filters.Event;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.Participant;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import static com.mycompany.makeanev2.Utils.CheckPermission.checkBlockUser;
import static com.mycompany.makeanev2.Utils.CheckPermission.checkNotLogin;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DeleteEventFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //определяем залогинишвегося из сессии
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User userInSession = AuthUtils.getLoginedUser(session);

        try {
            checkNotLogin(userInSession);
            checkBlockUser(userInSession);

            String id_event = (String) request.getParameter("id_event"); //параметры Object, приводим к String
            if (id_event == null) {
                throw new EventException("Не указан идентификатор мероприятия к удалению");
            }
            
            Connection con = DbConnection.getConnection();
            Event event = EventDbQuery.selectEvent(con, Integer.parseInt(id_event));
            List<Participant> participants = EventDbQuery.selectParticipantsOfEvent(con, event);
            con.close();

            event.setParticipants(participants);

            CheckPermission.checkDeleteEvent(userInSession, event);

            request.setAttribute("event", event);
            //идем дальше
            chain.doFilter(request, response);

            //блок исключений, если что-то пошло не так, прервываем загрузку страницы и выдаем пользователю сообщение о проблеме    
        } catch (UserException | EventException | SQLException | NamingException | NumberFormatException | NullPointerException ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/events"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }
}
