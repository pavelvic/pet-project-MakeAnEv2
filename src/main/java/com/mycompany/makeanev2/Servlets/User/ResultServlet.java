package com.mycompany.makeanev2.Servlets.User;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*обработка ситуации, когда нужно вывести какую-либо информацию пользователю (ошибка, результат операции и проч)
URL /resultpage */
public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //открываем страницу с результатом, вывод сообщения напрямую из сессии (см. исходный код jsp-страницы)
        request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
    }

}
