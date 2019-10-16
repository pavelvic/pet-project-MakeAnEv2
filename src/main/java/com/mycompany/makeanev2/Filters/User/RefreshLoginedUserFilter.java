package com.mycompany.makeanev2.Filters.User;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*выполняется при каждой загрукзке страницы, обновляет пользователя в сессии на основании актуальных данных в БД*/
public class RefreshLoginedUserFilter implements Filter {

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

        //если пользователь не залогинился, просто идем дальше, но если залогинен - обновляем в сессии для актуальности
        if (userInSession == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            //получаем пользователя из БД
            Connection con = DbConnection.getConnection();
            User userInDB = UserDbQuery.findUser(con, userInSession.getUsername());
            con.close();

            //если пользователь был удалён, то просто чистим сессию и куки и идем дальше
            if (userInDB == null) {
                AuthUtils.deleteLoginedUser(session);
                AuthUtils.deleteLoginedUserCookie((HttpServletResponse) response, userInSession);
                chain.doFilter(request, response);
                return;
            }

            //обновляем его в сессии и открываем страницу
            AuthUtils.refreshLoginedUser(req, (HttpServletResponse) response, userInDB);
            chain.doFilter(request, response);

            //TODO: кажется неэффективным ходить в БД и обновлять пользователя в сессии постоянно
            //подумать как можно управлять сессиями более эффективно
            //проблема в изменении критичных данных пользователя (который в сессии) в БД
            //например, поменяли группу полномочий, а в сессии того самого пользователя группа все та же, в результате будет доступ куда не надо
            //идея: пре редактировании пользователей получать доступ к сессии такого пользователя (если она активна)
            //и подменять (на уровне сервера) пользвателя в этой сессии
            //для этого надо понять как можно из сервлетов управлять сессиями, хранящимися на сервере, как найти нужную и как ее можно редактировать (если это вообще возможно)
            //блок исключений, если что-то пошло не так прервываем загрузку страницы и выдаем пользователю сообщение о проблеме
        } catch (SQLException | NamingException ex) {
            request.setAttribute("resultString", "Ошибка! : " + ex.toString());
            request.setAttribute("redirect", "/");
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
        }
    }
}