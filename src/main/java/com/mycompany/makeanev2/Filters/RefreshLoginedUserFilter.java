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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        User userInSession = AuthUtils.getLoginedUser(session);

        if (userInSession == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            //получаем пользователя из БД
            Connection con = DbConnection.getConnection();
            User userInDB = DbQuery.findUser(con, userInSession.getUsername());
            con.close();

            //если пользователь был удалён, то просто чистим сессию и куки
            if (userInDB == null) {
                AuthUtils.deleteLoginedUser(session);
                AuthUtils.deleteLoginedUserCookie((HttpServletResponse) response, userInSession);
                chain.doFilter(request, response);
                return;
            }

            //если пользователя изменили (например, группу пользователей), обновляем его в сессии
            if (!userInSession.equals(userInDB)) {
                AuthUtils.refreshLoginedUser(req, (HttpServletResponse) response, userInDB);
            }
            chain.doFilter(request, response);
        } catch (SQLException | NamingException ex) {
            request.setAttribute("resultString", "Ошибка! : " + ex.toString());
            request.setAttribute("redirect", "/");
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
        }
        //}
    }
}
