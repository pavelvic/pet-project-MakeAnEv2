package com.mycompany.makeanev2.Filters;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.DbQuery;
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

/*сервлет фильтр вызывается при загрузке каждой страницы и проверяем есть ли подходящий куки-файл
, если есть - загружает пользователя в сессию, что позволяет не логиниться*/
public class CookieFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //получаем сессию из http-запроса
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        //получаем залогиненного пользователя
        User userInSession = AuthUtils.getLoginedUser(session);

        //при нахождении залогиненного пользователя ставим признак об отсутсвии необходимости проверять куки и идём дальше
        if (userInSession != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }

        try {
            //сюда попадаем, если пользователь не нашёлся в сесии
            //получаем признак проверяли ли ранее куки
            String cheked = (String) session.getAttribute("COOKIE_CHECKED");

            //если не проверяли
            if (cheked == null) {
                String username = AuthUtils.getLoginedUserCookie(req); //получаем имя пользователя из куки файлов
                Connection con = DbConnection.getConnection();
                User user = DbQuery.findUser(con, username); //формируем полноценный объект из БД
                con.close();
                AuthUtils.storeLoginedUser(session, user); //записываем в сессию
                session.setAttribute("COOKIE_CHECKED", "CHECKED"); //ставим признак, что куки проверили и подгрузили в сессию пользователя
            }

            chain.doFilter(request, response); //идем дальше
            //!!! проверка выполняется при каждой загрузке любой страницы!

            //блок исключений, если что-то пошло не так прервываем загрузку страницы и выдаем пользователю сообщение о проблеме
        } catch (SQLException | NamingException ex) {
            request.setAttribute("resultString", "Ошибка! : " + ex.toString());
            request.setAttribute("redirect", "/");
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
        }
    }
}
