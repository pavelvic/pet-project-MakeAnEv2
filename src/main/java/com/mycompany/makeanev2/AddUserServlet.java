package com.mycompany.makeanev2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//сделать комментарии, сервлет для обработки вывода инфы на страницу
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/adduser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultString = null;
        //request по умолчению в ISO-8859-1 ???, БД - в UTF-8. 
        //Заранее ставим правильну кодировку, иначе в БД пишет в ISO из за чего проблема с кириллицей
        request.setCharacterEncoding("UTF-8");
        User user = null;
        try {
            Connection con = DbConnection.getConnection();
             
            String username = (String) request.getParameter("username");
            String password = (String) request.getParameter("password");
            String email = (String) request.getParameter("email");
            String phone = (String) request.getParameter("phone");
            String name = (String) request.getParameter("name");
            String surname = (String) request.getParameter("surname");
            String comment = (String) request.getParameter("comment");

          user = new User(0, username, password, email, phone, name, surname, comment);

            //выполняем проверки значений с генерацией исключений UserException
            user.checkUsername();
            user.checkEmail();
            user.checkPhone();
            user.checkPassword();

            //добавляем запись в БД и устанавливаем сообщение об успехе
            DbQuery.insertUser(con, user);
            con.close();
            resultString = "Пользователь добавлен";
            user = null; //обнуляем экземпляр, чтобы на старнице вывелись пустые поля

        } catch (SQLException | NamingException | UserException ex) { //Недосутпно соединение или ошмбка драйвера или не прошли проверку введеных значений

            resultString = "Ошибка! " + ex.toString();

        } finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/adduser.jsp").forward(request, response);
        }

    }

}
