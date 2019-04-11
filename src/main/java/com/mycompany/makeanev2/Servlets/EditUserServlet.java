package com.mycompany.makeanev2.Servlets;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.Utils.DbQuery;
import com.mycompany.makeanev2.Utils.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUserServlet extends HttpServlet {

   private User userToUpdate;
    //все существующие в БД группы для формирования списка на странице
   /*использование этого экземпляра user: doGet - получаем объект по id, 
   в doPost экземпляр создается по ЛЮБЫМ введенным данным (даже в случае не успеха UPDATE в БД),
   что необходимо для передачи в finally метода doPost этого объекта на страницу editsuer
   глобальная переменная user позволяет правильно маршутизировать в finally: при exeption в doPost объект не создается
   и страница edituser выводилась бы пустая, но поскольку глобально при doGet уже был определен user, 
   он и выводится на страницу при exception, без глобального экземпляра такое взаимодействие было бы невозможным*/
   private RequestDispatcher dispatcher;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        userToUpdate = (User)request.getAttribute("user");
        dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
        dispatcher.forward(request, response); //открываем страницу

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
            
            Map<String,String> updateUser = new HashMap<>();
            
            updateUser.put("id_user", id_user);
            updateUser.put("group_id", splitidname[0]);
            updateUser.put("groupname", splitidname[1]);
            updateUser.put("username", username);
            updateUser.put("password", null);
            updateUser.put("passwordStr", null);
            updateUser.put("email", email);
            updateUser.put("phone", phone);
            updateUser.put("name", name);
            updateUser.put("surname", surname);
            updateUser.put("comment", comment);
            
            userToUpdate.applyChanges(updateUser);
            
            userToUpdate.checkUsernamePattern(); //проверка формата e-mail
            userToUpdate.checkEmailPattern(); //проверка формата e-mail
            userToUpdate.checkPhonePattern(); //проверка формата телефона
            
            List<User> allUsers = DbQuery.selectUserExcept(con, id_user);
            userToUpdate.checkUniqueUser(allUsers);//проверка на уникальность пользователя (username, e-mail, phone - не должны совпадать с существующими)
            
            DbQuery.updateUser(con, userToUpdate);
            con.close();
            resultString = "Изменения внесены";
            
            //TODO если обновляем своего пользователя, надо обновить его данные в session и куках


        } catch (SQLException | NamingException | UserException ex) {
            resultString = "Ошибка! " + ex.toString();
        } finally {
            request.setAttribute("resultString", resultString); //передали результат действия
            request.setAttribute("user", userToUpdate); //сохранили что ввели и передали на страницу для отображения
            dispatcher.forward(request, response); //показали страницу
        }
    }
}
