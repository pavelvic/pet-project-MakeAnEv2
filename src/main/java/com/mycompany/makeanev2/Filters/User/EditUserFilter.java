package com.mycompany.makeanev2.Filters.User;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.UserGroup;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.UserDbQuery;
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

/*фильтр для предобработки события редактирования данных пользователя (кроме пароля) (при переходе в сервлет EditUserServlet, URL '/edituser?id_user = ?') */
public class EditUserFilter implements Filter {

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
            Connection con = DbConnection.getConnection();
            //подготовить список групп пользователей для формирования списка на странице (
            //при редактировании нужно дать перечень всех групп пользователей для выбора из списка
            List<UserGroup> userGroups = UserDbQuery.selectUserGroup(con);
            request.setAttribute("usergroups", userGroups); //сохраняем список в запросе и выводим на страницу в Servlet

            //проверяем полномочия, получаем пользователя к которому хотим получить доступ
            String id_user = (String) request.getParameter("id_user"); //параметры - Object, приводим к String

            User userToAccess = UserDbQuery.selectUser(con, Integer.parseInt(id_user));
            con.close();

            //определяем залогинишвегося
            HttpServletRequest req = (HttpServletRequest) request;
            HttpSession session = req.getSession();
            User userInSession = AuthUtils.getLoginedUser(session);

            //проверяем есть ли доступ у текущего пользователя к редактированию запрашиваемого
            //своего пользователя можно редактировать, только админы (группа 2) и суперпользователи (группа 1) могут редактировтаь чужих
            CheckPermission.checkEditUserAccess(userInSession, userToAccess);

            //сохраняем в запрос редактируемого для дальнейшего исопльзования в Servlet
            request.setAttribute("user", userToAccess);

            //если все хорошо и исключений мы не получили, устанавливаем диспетчера для перехода в сервлете
            //в зависимости от группы пользователя залогиненного, определяем какая страница будет открыта, маршрут сохраняется в спец объекте dispatcher
            //которого сохраняем в сессии, в коде Servlet этот объект указывает какую страницу надо открыть
            //позволяет настроить свои страницы для каждой группы пользователей, со своим оформлением и функционалом
            switch (userInSession.getGroup_id()) {
                case 1:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/ownerview/edituser.jsp"));
                    break;
                case 2:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/adminview/edituser.jsp"));
                    break;
                case 3:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/managerview/edituser.jsp"));
                    break;
                case 4:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/userview/edituser.jsp"));
                    break;
                case 5:
                    throw new UserException("Доступ запрещен. Пользователь заблокирован"); //на всякий случай...

                default:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/login.jsp")); //на всякий случай...
            }

            //идем дальше, выполняется Servlet
            chain.doFilter(request, response);

            //блок исключений, если что-то пошло не так прервываем загрузку страницы и выдаем пользователю сообщение о проблеме
        } catch (SQLException | NamingException | UserException | NumberFormatException | NullPointerException ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }
}
