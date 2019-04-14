package com.mycompany.makeanev2.Servlets;

import com.mycompany.makeanev2.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User)request.getAttribute("user");
        RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
        dispatcher.forward(request, response); //открываем страницу 
       
    }
}
