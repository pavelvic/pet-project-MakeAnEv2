package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.Utils.UserDbQuery;
import com.mycompany.makeanev2.Utils.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация события регистрации нового пользователя, URL /register   */
public class RegisterUserServlet extends HttpServlet {

    private RequestDispatcher dispatcher;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //открываем страницу
        request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultString = null; //переменная для фиксации результата операции
        User user = null; //регистрируемый пользователь для записи в БД

        try {
            //разбираем введенные в форму регистрации параметры из http-запроса
            String username = (String) request.getParameter("username");
            String password = (String) request.getParameter("password");
            String email = (String) request.getParameter("email");
            String phone = (String) request.getParameter("phone");
            String name = (String) request.getParameter("name");
            String surname = (String) request.getParameter("surname");
            String comment = (String) request.getParameter("comment");

            //по дефолту при регистрации указывает группу пользователей 4 - Пользователь; Создаем пользователя
            user = new User(0, 4, "Пользователь", username, password.hashCode(), password, email, phone, name, surname, comment);

            //выполняем проверки введенных значений с генерацией исключений UserException
            user.checkUsernamePattern();
            user.checkEmailPattern();
            user.checkPhonePattern();
            user.checkPasswordPattern();

            //реализуем проверку пользователя на уникальность (одинаковых имени пользователя и e-mail в БД быть не может)
            Connection con = DbConnection.getConnection();
            List<User> allUsers = UserDbQuery.selectUser(con);
            List<User> remUsers = new ArrayList<>(); //генерируем лист исключений для проверки уникальности (пустой для данного случая, поскольку это новый пользователь и исклюений для проверки не может быть, сравниваем со всеми пользователями в БД)
            user.checkUniqueUser(allUsers, remUsers);//проверка на уникальность пользователя (username, e-mail, phone - не должны совпадать с существующими)

            //добавляем запись в БД и устанавливаем сообщение об успехе
            UserDbQuery.insertUser(con, user);
            con.close();
            resultString = "Регистрация успешно выполнена. Теперь можете войти на сайт!";
            dispatcher = request.getRequestDispatcher("/WEB-INF/resultpage.jsp"); //маршрутизируем на страницу с сообщением
            //user = null; //обнуляем экземпляр, чтобы на старнице вывелись пустые поля

            //если что-то пошло не так, фиксируем ошибку
        } catch (SQLException | NamingException | UserException ex) {
            resultString = "Ошибка! " + ex.toString();
            dispatcher = request.getRequestDispatcher("/WEB-INF/register.jsp"); //при ошибке открываем снова эту же страницу

            //выводим результат операции
        } finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("user", user);
            dispatcher.forward(request, response); //идем на страницу в завиимости от dispatcher
        }
    }
}
