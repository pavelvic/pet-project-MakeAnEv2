package com.mycompany.makeanev2.Servlets;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString;
        request.setAttribute("user", AuthUtils.getLoginedUser(request.getSession()));

        User loginedUser = AuthUtils.getLoginedUser(request.getSession());
        if (loginedUser != null) {
            resultString = "Вы вышли";
            
            //обнуляем куки
            AuthUtils.deleteLoginedUserCookie(response, loginedUser);
            
            //обнуляем пользователя в сессии
            AuthUtils.deleteLoginedUser(request.getSession());
            request.setAttribute("redirect", "/");
        } else {
            resultString = "Вы еще не вошли";
            request.setAttribute("redirect", "/login");
        }

        request.setAttribute("resultString", resultString);
        request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
    }
}
