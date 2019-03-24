package com.mycompany.makeanev2.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //request.setAttribute("user", AuthUtils.getLoginedUser(request.getSession()));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        
    }
}
