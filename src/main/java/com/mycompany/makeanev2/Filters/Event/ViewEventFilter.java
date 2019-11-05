
package com.mycompany.makeanev2.Filters.Event;


import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*фильтр для предобработки события просмотра параметров пользователя (при переходе в сервлет ViewUserServlet, URL '/viewuser?id_user = ?') */
public class ViewEventFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
           
             //получаем мероприятие к которому хотим получить доступ
            Connection con = DbConnection.getConnection();
            String id_event = (String) request.getParameter("id_event"); //параметры Object, приводим к String
            Event evToAccess = EventDbQuery.selectEvent(con, Integer.parseInt(id_event));
            con.close();
            
            //сохраняем в запрос самого пользователя для дальнейшего исопльзования
            request.setAttribute("event", evToAccess);
            
            //определяем залогинишвегося
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            User userInSession = AuthUtils.getLoginedUser(session);
            
            CheckPermission.checkNotLogin(userInSession);


            //если все хорошо и исключений мы не получили, устанавливаем диспетчера для перехода в сервлете
            //в зависимости от группы пользователя залогиненного, определяем какая страница будет открыта, маршрут сохраняется в спец объекте dispatcher
            //которого сохраняем в сессии, в коде Servlet этот объект указывает какую страницу надо открыть
            //позволяет настроить свои страницы для каждой группы пользователей, со своим оформлением и функционалом
            switch (userInSession.getGroup_id()) {
                case 1:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/ownerview/viewevent.jsp"));
                    break;
                case 2:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/adminview/viewevent.jsp"));
                    break;
                case 3:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/managerview/viewevent.jsp"));
                    break;
                case 4:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/userview/viewevent.jsp"));
                    break;
                case 5:
                    throw new UserException("Доступ запрещен. Пользователь заблокирован"); //на всякий случай...

                default:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/login.jsp")); //на всякий случай...
            }
            chain.doFilter(request, response);

            //блок исключений, если что-то пошло не так прервываем загрузку страницы и выдаем пользователю сообщение о проблеме
        } catch (SQLException | NamingException | UserException | NumberFormatException | NullPointerException  ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }
}