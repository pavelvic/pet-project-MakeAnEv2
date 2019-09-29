package com.mycompany.makeanev2.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация события открытия главной страницы, URL '/'  */
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //открываем главную страницу
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
