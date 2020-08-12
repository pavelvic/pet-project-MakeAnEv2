package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.User;
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

/*обработка события удаления пользователя после проверки всех полномочий, URL /deleteuser?id_user = ? */
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //информация о результате операции
        String resultString = null;

        //пользователь для удаления (сформирован ранее в Filter, где проверялось можно ли его удалять)
        User userToDelete = (User) request.getAttribute("user");

        try {
            Connection con = DbConnection.getConnection();
            UserDbQuery.deleteUser(con, userToDelete); //запрос в БД для удаления пользователя
            con.close();
            resultString = "Пользователь удалён"; //сообщаем результат операции

            //если что-то пошло не так, сообщаем иной результат операции с текстом ошибки
        } catch (SQLException | NamingException | NumberFormatException | NullPointerException ex) {
            resultString = "Ошибка! " + ex.getMessage();
        } //вывод результата операции 
        finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/"); //указываем откуда мы идем на страницу результата для настройки маршрутизации
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response);
        }
    }
}
