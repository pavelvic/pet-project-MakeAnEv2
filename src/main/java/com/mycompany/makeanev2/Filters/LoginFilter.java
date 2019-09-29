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

/*фильтр для предобработки события логина пользователя в систему (при переходе в сервлет LoginServlet, URL '/login') */
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //получаем злогиненного пользователя UserInSession (null - если его нет)
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User userInSession = AuthUtils.getLoginedUser(session);

        try {
            //проверяем возможность логирования, если UserInSession == null генерируем исключение UserException с текстом
            CheckPermission.checkLoginAccess(userInSession);

            //идем дальше
            chain.doFilter(request, response);

            //блок исключений, если что-то пошло не так прервываем загрузку страницы и выдаем пользователю сообщение о проблеме
        } catch (UserException ex) {
            request.setAttribute("resultString", "Ошибка! " + ex.toString());
            request.setAttribute("redirect", "/");
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
        }
    }
}