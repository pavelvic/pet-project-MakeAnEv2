package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
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
import javax.servlet.http.HttpSession;

/*реализация события логина пользователя (вход в систему), URL /login  */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //открываем страницу
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //реализация нажатия кнопки "Залогиниться"
        String username = request.getParameter("username"); //получаем с формы сайта "имя пользователя"
        String password = request.getParameter("password"); //пароль (текст)

        boolean remember = "Y".equals(request.getParameter("rememberMe")); //галка "Заполнить меня"

        User user = null; //для формрования входящего на сайт пользователя
        String redirectString; //маршрутизация с этой страницы
        String errorString = null; //для фиксации ошибок

        //redirectString = request.getContextPath() + "/login"; //по умолчанию переходим на эту же страницу
        try {
            //если пользователь не ввел имя и пароль, фиксируем ошибку
            if (username == null || password == null || username.length() == 0 || password.length() == 0) {
                errorString = "Не заполнены Имя пользователя и / или пароль";
                request.setAttribute("errorString", errorString);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                //если ввел
            } else {
                Connection con = DbConnection.getConnection();
                user = UserDbQuery.findUser(con, username, password); //ищем в БД
                con.close();

                //если такой пользователь в базе не найден
                if (user == null) {
                    errorString = "Имя пользователя или пароль не корректны"; //фиксируем ошибку
                    request.setAttribute("errorString", errorString);
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    //если найден
                } else {
                    CheckPermission.checkBlockUser(user); //проверим блокирован ли пользователь, который пытается войти
                    HttpSession session = request.getSession(); //получаем сессию
                    AuthUtils.storeLoginedUser(session, user);// записываем туда пользователя (это и есть операция входа на сайт)
                    //redirectString = request.getContextPath() + "/"; //отправляем на домашнюю страницу

                    //Дополнительно: если установлена галочка "Запомнить меня" - формируем куки файл
                    //если не установлена, удаляем куки файл
                    if (remember) {
                        AuthUtils.storeLoginedUserCookie(response, user);
                    } else {
                        AuthUtils.deleteLoginedUserCookie(response, user);
                    }
                }
            }
            response.sendRedirect(request.getContextPath() + "/");
            //если что-то пошло не так фиксируем ошибку
        } catch (SQLException | NamingException | UserException ex) {
            errorString = "Ошибка! " + ex.toString();
            //выводим результат операциии + ошибку если имела место, или идем на домашнюю старницу
        }
    }
}
