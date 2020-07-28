package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.User;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация просмотра информации о пользователе
URL /viewuser?id_user = ?*/
public class ViewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getAttribute("user"); //получаем пользователя для просмотра (сформирован на этапе фильтра и проверен можно ли его смотреть)
        RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher"); //получаем какую страницу открыть (разные группы пользовтеля имею свои страницы со своим интерфейсом и функциями)
        dispatcher.forward(request, response); //открываем страницу  
    }
}
