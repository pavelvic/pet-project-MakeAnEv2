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
            
        try {
            Connection con = DbConnection.getConnection();
            
            String username = (String) request.getParameter("username");
            String password = (String) request.getParameter("password");
            String email = (String) request.getParameter("email");
            String phone = (String) request.getParameter("phone");
            String name = (String) request.getParameter("name");
            String surname = (String) request.getParameter("surname");
            String comment = (String) request.getParameter("comment");
            
            User user = new User(0, username, password, email, phone, name, surname,comment);

            if (("".equals(username)) | ("".equals(email)) | ("".equals(password))) {
                resultString = "Не заполнены обязательные* поля!";
            }
            if (resultString == null) {
                
                DbQuery.insertUser(con, user);
                con.close();
                resultString = "Пользователь добавлен";
                
            }

        } catch (SQLException | NamingException ex) { //Недосутпно соединение или ошмбка драйвера

            resultString = "Ошибка соединения с базой данных! "+ex.getMessage();

        } finally {
            
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", request.getServletPath()); //указываем откуда мы идем на страницу результата для настройки маршрутизации
            
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response);
        }

    }

}
