package com.mycompany.makeanev2.Filters.User;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.UserDbQuery;
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
public class ViewUserFilter implements Filter {

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
            //получаем пользователя к которому хотим получить доступ
            Connection con = DbConnection.getConnection();
            String id_user = (String) request.getParameter("id_user"); //параметры Object, приводим к String
            User userToAccess = UserDbQuery.selectUser(con, id_user);
            con.close();

            //определяем залогинишвегося
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            User userInSession = AuthUtils.getLoginedUser(session);

            //проверяем есть ли доступ до данному запросу
            //свой профиль смотреть можно (userInSession = userToAccess), также 1 - Суперпользователь и 2 - Администратор могут смотреть других 
            //выдается исключение UserException если фиксируются попытки просмотра не разрешенных пользователей
            CheckPermission.checkViewUserAccess(userInSession, userToAccess);

            //сохраняем в запрос самого пользователя для дальнейшего исопльзования
            request.setAttribute("user", userToAccess);

            //если все хорошо и исключений мы не получили, устанавливаем диспетчера для перехода в сервлете
            //в зависимости от группы пользователя залогиненного, определяем какая страница будет открыта, маршрут сохраняется в спец объекте dispatcher
            //которого сохраняем в сессии, в коде Servlet этот объект указывает какую страницу надо открыть
            //позволяет настроить свои страницы для каждой группы пользователей, со своим оформлением и функционалом
            switch (userInSession.getGroup_id()) {
                case 1:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/ownerview/viewuser.jsp"));
                    break;
                case 2:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/adminview/viewuser.jsp"));
                    break;
                case 3:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/managerview/viewuser.jsp"));
                    break;
                case 4:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/userview/viewuser.jsp"));
                    break;
                case 5:
                    throw new UserException("Доступ запрещен. Пользователь заблокирован"); //на всякий случай...

                default:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/login.jsp")); //на всякий случай...
            }
            chain.doFilter(request, response);

            //блок исключений, если что-то пошло не так прервываем загрузку страницы и выдаем пользователю сообщение о проблеме
        } catch (SQLException | NamingException | UserException ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }
}