package com.mycompany.makeanev2.Filters;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*фильтр для предобработки события просмотра списка пользователей (при переходе в сервлет UserListServlet, URL '/userlist') */
public class UserListFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //определяем залогинишвегося
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User userInSession = AuthUtils.getLoginedUser(session);

        try {
            //проверяем есть ли доступ к списку пользователей (только 1 - Суперпользователь и 2 - Администратор)
            //генерируем исключение UserException при непрохождении проверки
            CheckPermission.checkUserListAccess(userInSession);

            //если все хорошо и исключений мы не получили, устанавливаем диспетчера для перехода в сервлете
            //в зависимости от группы пользователя залогиненного, определяем какая страница будет открыта, маршрут сохраняется в спец объекте dispatcher
            //которого сохраняем в сессии, в коде Servlet этот объект указывает какую страницу надо открыть
            //позволяет настроить свои страницы для каждой группы пользователей, со своим оформлением и функционалом
            switch (userInSession.getGroup_id()) {
                case 1:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/ownerview/userlist.jsp"));
                    break;
                case 2:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/WEB-INF/adminview/userlist.jsp"));
                    break;
                case 3:
                    throw new UserException("Доступ запрещен. Недостаточно полномочий Менежера");
                case 4:
                    throw new UserException("Доступ запрещен. Недостаточно полномочий Пользователя");
                case 5:
                    throw new UserException("Доступ запрещен. Пользователь заблокирован"); //на всякий случай...

                default:
                    request.setAttribute("dispatcher", request.getRequestDispatcher("/login.jsp")); //на всякий случай...
            }
            //идем дальше
            chain.doFilter(request, response);

            //блок исключений, если что-то пошло не так прервываем загрузку страницы и выдаем пользователю сообщение о проблеме  
        } catch (UserException ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/");
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }
}
