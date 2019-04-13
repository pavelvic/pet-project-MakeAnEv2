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

public class ResetPasswordFilter implements Filter {

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
            //проверяем полномочия
            CheckPermission.checkResetPassword(userInSession);
            
            //идем дальше
            chain.doFilter(request, response);
            
        } catch (UserException ex) {
            String errorString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }

    }
}
