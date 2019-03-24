package com.mycompany.makeanev2.Servlets;

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
        
        //request.setAttribute("user", AuthUtils.getLoginedUser(request.getSession()));
        
        AuthUtils.deleteLoginedUser(request.getSession());
        request.setAttribute("resultString", "Вы вышли");
        request.setAttribute("redirect", "/");
        request.getRequestDispatcher("/resultpage.jsp").forward(request, response);
        
    }
}
