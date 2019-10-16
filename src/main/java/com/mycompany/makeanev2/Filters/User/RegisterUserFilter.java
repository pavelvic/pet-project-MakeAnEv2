package com.mycompany.makeanev2.Filters.User;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*фильтр для предобработки события регистрации пользователя (при переходе в сервлет RegisterUserServlet, URL '/register') */
public class RegisterUserFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //получаем пользователя из сессии
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        User userInSession = AuthUtils.getLoginedUser(session);

        //если в сесси есть пользователь, регистрация невозможно, выдаем исключение с соотв сообщением
        if (userInSession != null) {
            try {
                throw new UserException("Вы уже вошли в систему под пользователем " + userInSession.getUsername() + ". Регистрация невозомжна");

                //блок исключений, если что-то пошло не так прервываем загрузку страницы и выдаем пользователю сообщение о проблеме
                //в данном случае только ошибка попытка регистрации при имеющемся пользователе
            } catch (UserException ex) {
                String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
                request.setAttribute("resultString", errorString);
                request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была 
                request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
            }
        }
        chain.doFilter(request, response);
    }
}