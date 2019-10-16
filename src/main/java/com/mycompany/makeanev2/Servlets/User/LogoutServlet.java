package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация события выхода с сайта пользователя, URL /logout  */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString; //для фиксации результат операции

        //получаем залогиневшегося пользователя
        User loginedUser = AuthUtils.getLoginedUser(request.getSession());
        //если находится залогиневшийся польз выполняем выход: удаляем из сессии и чистим куки
        if (loginedUser != null) {
            resultString = "Вы вышли";
            
            //обнуляем куки
            AuthUtils.deleteLoginedUserCookie(response, loginedUser);
            
            //обнуляем пользователя в сессии
            AuthUtils.deleteLoginedUser(request.getSession());
            request.setAttribute("redirect", "/"); //устанавливаем маршрутизацию на главную страницу
        //если пользователь в сесси не найден
        } else {
            resultString = "Вы еще не вошли"; //фиксируем результат операции
            request.setAttribute("redirect", "/login"); //маршрутизируем на страницу логина
        }

        //передеам результат на страницу с результатом операции и переходим туда
        request.setAttribute("resultString", resultString);
        request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
    }
}