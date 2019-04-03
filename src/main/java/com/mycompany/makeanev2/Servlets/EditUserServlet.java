package com.mycompany.makeanev2.Servlets;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Exceptions.UserException;
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

public class EditUserServlet extends HttpServlet {

   private User user;
    //все существующие в БД группы для формирования списка на странице
   /*использование этого экземпляра user: doGet - получаем объект по id, 
   в doPost экземпляр создается по ЛЮБЫМ введенным данным (даже в случае не успеха UPDATE в БД),
   что необходимо для передачи в finally метода doPost этого объекта на страницу editsuer
   глобальная переменная user позволяет правильно маршутизировать в finally: при exeption в doPost объект не создается
   и страница edituser выводилась бы пустая, но поскольку глобально при doGet уже был определен user, 
   он и выводится на страницу при exception, без глобального экземпляра такое взаимодействие было бы невозможным*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String resultString;
        User userInSession; //сюда помещаем залогинившегося пользователя
        try {
            Connection con = DbConnection.getConnection();

            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            user = DbQuery.selectUser(con, id_user);
            con.close(); //закрываем соединение сразу после получения данных
            
            userInSession = AuthUtils.getLoginedUser(request.getSession());
            //CheckPermission.checkEditUserAccess(userInSession, user); //проверим возможносто доступа
            //направим на правильную страницу
            String redirect = CheckPermission.getEditUserRedirect(userInSession, user);
            
            request.setAttribute("user", user); //передаем объект на страницу для настройки view
           
            request.getRequestDispatcher(redirect).forward(request, response); //открываем страницу 
        } catch (SQLException | NamingException | NumberFormatException | UserException ex) {
            resultString = "Ошибка! " + ex.toString(); //информация об ошибке
            request.setAttribute("resultString", resultString);
            request.setAttribute("redirect", "/userlist"); //указываем чтобы маршрутизация с resultpage была на userlist
            request.getRequestDispatcher("/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String resultString = null;
        try {
            Connection con = DbConnection.getConnection();
            
            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            String idnamegroup = (String) request.getParameter("idnamegroup");
            String username = (String) request.getParameter("username");
            String email = (String) request.getParameter("email");
            String phone = (String) request.getParameter("phone");
            String name = (String) request.getParameter("name");
            String surname = (String) request.getParameter("surname");
            String comment = (String) request.getParameter("comment");
            
            //отделим id_group и Name от параметра на странице idnamegroup (паттерн: [id_group]:[название группы])
            String splitidname[] = idnamegroup.split(":"); //полученный массив используем для создания user
            
            
            user = new User(Integer.parseInt(id_user), Integer.parseInt(splitidname[0]),
                    splitidname[1], username, 0, email, phone, name, surname, comment);
            
            user.checkUsername();
            user.checkEmail();
            user.checkPhone();
            
            DbQuery.updateUser(con, user);
            con.close();
            resultString = "Изменения внесены";
            
            //TODO если обновляем своего пользователя, надо обновить его данные в session и куках


        } catch (SQLException | NamingException | UserException ex) {
            resultString = "Ошибка ! " + ex.toString();
        } finally {
            request.setAttribute("resultString", resultString); //передали результат действия
            request.setAttribute("user", user); //сохранили что ввели и передали на страницу для отображения
            String redirect = (String) request.getAttribute("redirUsergroup");
            request.getRequestDispatcher(redirect).forward(request, response); //показали страницу
        }
    }
}
