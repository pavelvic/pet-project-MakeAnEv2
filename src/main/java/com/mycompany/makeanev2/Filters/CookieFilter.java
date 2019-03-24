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

public class CookieFilter implements Filter {
//Заранее ставим правильну кодировку, иначе в БД пишет в ISO из за чего проблема с кириллицей

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        User userInSession = AuthUtils.getLoginedUser(session);

        if (userInSession != null) {
            session.setAttribute("COOKIE_CHECKED", "CHECKED");
            chain.doFilter(request, response);
            return;
        }

        try {
            Connection con = DbConnection.getConnection();
            String cheked = (String) session.getAttribute("COOKIE_CHECKED");

            if (cheked == null) {
                String username = AuthUtils.getLoginedUserCookie(req);
                User user = DbQuery.findUser(con, username);
                AuthUtils.storeLoginedUser(session, user);
                session.setAttribute("COOKIE_CHECKED", "CHECKED");
            }
            con.close();
            chain.doFilter(request, response);

        } catch (SQLException | NamingException ex) {
            request.setAttribute("resultString", "Ошибка: " + ex.toString());
            request.setAttribute("redirect", "/"); //указываем откуда мы идем на страницу результата для настройки маршрутизации
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response);
        }
    }
}
