package com.mycompany.makeanev2.Servlets;

import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.AuthUtils;
import com.mycompany.makeanev2.Utils.CheckPermission;
import com.mycompany.makeanev2.Utils.DbQuery;
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

//сделать комментарии, сервлет для обработки вывода инфы на страницу
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean remember = "Y".equals(request.getParameter("rememberMe"));

        User user = null;
        String redirectString;
        String errorString = null;

        redirectString = "/login.jsp";

        try {
            if (username == null || password == null || username.length() == 0 || password.length() == 0) {
                errorString = "Не заполнены Имя ользователя и / или пароль";
            } else {
                Connection con = DbConnection.getConnection();
                user = DbQuery.findUser(con, username, password);
                con.close();
                CheckPermission.checkBlockUser(user); //проверим блокирован ли пользователь, который пытается войти
                if (user == null) {

                    errorString = "Имя пользователя или пароль не корректны";
                } else {

                    HttpSession session = request.getSession();
                    AuthUtils.storeLoginedUser(session, user);
                    redirectString = "/";

                    if (remember) {
                        AuthUtils.storeLoginedUserCookie(response, user);
                    } else {
                        AuthUtils.deleteLoginedUserCookie(response, user);
                    }
                }
            }
        } catch (SQLException | NamingException | UserException ex) {
            errorString = "Ошибка! " + ex.toString();
        } finally {
            request.setAttribute("errorString", errorString);
            request.getRequestDispatcher(redirectString).forward(request, response);
        }
    }
}
