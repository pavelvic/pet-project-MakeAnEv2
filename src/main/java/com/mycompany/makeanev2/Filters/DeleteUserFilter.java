package com.mycompany.makeanev2.Filters;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
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

public class DeleteUserFilter implements Filter {

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
            
            //получаем пользователя к которому запрашивается доступ
            Connection con = DbConnection.getConnection();
            String id_user = (String) request.getParameter("id_user"); //параметры Object, приводим к String
            User userToAccess = DbQuery.selectUser(con, id_user);
            con.close();
            
            //проверяем полномочия
            CheckPermission.checkDeleteUser(userInSession, userToAccess);
            
            //сохраняем в запрос пользоваеля
            request.setAttribute("user", userToAccess);
            
            //идем дальше
            chain.doFilter(request, response);
            
        } catch (UserException | SQLException | NamingException ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }

    }
}