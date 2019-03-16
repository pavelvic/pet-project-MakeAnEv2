package com.mycompany.makeanev2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserServlet extends HttpServlet {

   User user;
   /*использование этого экземпляра user: doGet - получаем объект по id, 
   в doPost экземпляр создается по ЛЮБЫМ введенным данным (даже в случае не успеха UPDATE в БД),
   что необходимо для передачи в finally метода doPost этого объекта на страницу editsuer
   глобальная переменная user позволяет правильно маршутизировать в finally: при exeption в doPost объект не создается
   и страница edituser выводилась бы пустая, но поскольку глобально при doGet уже был определен user, 
   он и выводится на страницу при exception, без глобального экземпляра такое взаимодействие было бы невозможным*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString = null;
        
        try {
            Connection con = DbConnection.getConnection();

            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            user = DbQuery.selectUser(con, id_user);
            con.close(); //закрываем соединение сразу после получения данных
            request.setAttribute("user", user); //передаем объект на страницу для настройки view

            request.getRequestDispatcher("/edituser.jsp").forward(request, response);
        } catch (SQLException | NamingException | NumberFormatException ex) {
            resultString = "Ошибка! " + ex.getMessage(); //информация об ошибке
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); //ставим кодировку базы чтобы передать параметры со страницы
        String resultString = null;
        try {
            Connection con = DbConnection.getConnection();
            
            //TODO есть смысл сделать формирование в конструкторе USER (передаем request)
            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            String username = (String) request.getParameter("username");
            String email = (String) request.getParameter("email");
            String phone = (String) request.getParameter("phone");
            String name = (String) request.getParameter("name");
            String surname = (String) request.getParameter("surname");
            String comment = (String) request.getParameter("comment");

            user = new User(Integer.parseInt(id_user), username, email, phone, name, surname,comment);
            
            user.checkUsername();
            user.checkEmail();
            user.checkPhone();
            
            DbQuery.updateUser(con, user);
            con.close();
            resultString = "Изменения внесены";


        } catch (SQLException | NamingException | UserException ex) {
            resultString = "Ошибка ! " + ex.toString();
        } finally {
            request.setAttribute("resultString", resultString); //передали результат действия
            request.setAttribute("user", user); //сохранили что ввели и передали на страницу для отображения
            request.getRequestDispatcher("/edituser.jsp").forward(request, response); //показали страницу
        }
    }
}
