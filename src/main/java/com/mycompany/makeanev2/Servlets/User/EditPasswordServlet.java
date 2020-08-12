package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.Utils.AuthUtils;
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

/*реализация события редактирования пароля, URL editpass?id_user=?*/
public class EditPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //открываем страницу
        request.getRequestDispatcher("/WEB-INF/editpass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //обработка кнопки изменения пароля
        //инициализируем сообщение о результате операции
        String resultString = "";

        //получаем идентификатор пользователя, которому меняем пароль
        String id_user = (String) request.getParameter("id_user");

        // получаем злогинившегося пользователя от чего имени выполняется операция
        User userInSession = AuthUtils.getLoginedUser(request.getSession());

        try {
            //получаем пароль для сравнения с текущим паролем, только при совпадении будет разрешено внести изменения
            //исключение - суперпользователь, при попытке изменить пароль у него не запрашивается текущий пароль во всех случаях, на то он и суперпользователь
            String actualPassword = (String) request.getParameter("actualPassword");
            String newPassword = (String) request.getParameter("newPassword");

            //получаем пользователя, для которого меняем пароль
            User user = (User) request.getAttribute("user");

            //инициализируем переменную для сравнения хэш-кодов, если пароль не пустой, записываем туда хэш код для сравнения с БД
            int actualHashCodePassword = -1;
            if (actualPassword != null) {
                actualHashCodePassword = actualPassword.hashCode();
            }

            //обработка нужна для нормальной работы суперпользователя, он не доджен вводить пароль пользователя для смены
            //разрешаем менять пароль если пароль изменяемого пользователя введен верно или если это супер пользователь (группа = 1)
            if ((actualHashCodePassword == user.getPassword()) || (userInSession.getGroup_id() == 1)) {
                user.setPassword(newPassword); //изменяем объект и устанавливаем новый пароль
                user.checkPasswordPattern(); //проверяем новый пароль объекта на корректность - UserException в случае не успеха
                Connection con = DbConnection.getConnection();
                UserDbQuery.updateUserPassword(con, user); //передаем в БД обновленный объект и фиксируем там изменения
                con.close();
                resultString = "Пароль изменён"; //фиксируем результат операции
            } else {
                resultString = "Пароль не совпадает. Введите правильный пароль"; //фиксируем результат операции: введен не совпадающий пароль
            }

            //фиксируем результат операции, если что-то пошло не так
        } catch (SQLException | NamingException | UserException ex) {
            resultString = "Ошибка! " + ex.toString();

            //выводим результат операции
        } finally {
            request.setAttribute("resultString", resultString); //сохраняем в http-запросе, передается на страницу
            request.getRequestDispatcher("/WEB-INF/editpass.jsp").forward(request, response); //открываем страницу
        }
    }
}
