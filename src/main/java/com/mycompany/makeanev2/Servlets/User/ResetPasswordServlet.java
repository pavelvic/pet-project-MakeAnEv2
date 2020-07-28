package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.Utils.UserDbQuery;
import com.mycompany.makeanev2.Utils.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация функционала сброса пароля (пользователь может сам сбросить себе пароль либо админ может сбросить пользователю)
URL '/resetpass?id_user = ?' */
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString = null; //страница с результатом операции
        String id_user = (String) request.getParameter("id_user"); //получаем идентификатор сбрасываемого пользователя

        try {
            Connection con = DbConnection.getConnection();
            UserDbQuery.updateUserPassword(con, Integer.parseInt(id_user)); //запрос меняет пароль на пароль по умолчанию
            con.close();
            resultString = "Пароль сброшен. Используйте пароль по умолчанию - '0' "; //информация и результате операции
            //если что-то пошло не так
        } catch (SQLException | NamingException | NumberFormatException | NullPointerException ex) {
            resultString = "Ошибка! " + ex.getMessage(); //в результате операции фиксируем информацию
        } finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/viewuser?id_user=" + id_user); //указываем откуда мы идем на страницу результата для настройки маршрутизации
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //показываем старницу с результатом
        }
    }
}
