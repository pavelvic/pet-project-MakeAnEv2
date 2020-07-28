package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Exceptions.UserException;
import com.mycompany.makeanev2.Utils.UserDbQuery;
import com.mycompany.makeanev2.Utils.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация события редактирования данных пользователя, URL /edituser?id_user=?*/
public class EditUserServlet extends HttpServlet {

    //пользователь, которого хотим поменять
    private User userToUpdate;

    //диспетчер для маршрутизации, указываем какую страницу открывать в зависимости от отработки кода в фильтре
    //информация о маршрутизации определена на этапе выполнения фильтра этого сервлета (на основании проверки полномочий), dispatcher размщен в сессии
    //достаем из сессии и выполняем маршрутизацию
    private RequestDispatcher dispatcher;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //перед открытием страницы
        //получаем данные из сессии для обработки в методе редактирования пользователя
        userToUpdate = (User) request.getAttribute("user");
        dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
        dispatcher.forward(request, response); //открываем страницу
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //по нажатии кнопки "Сохранить"
        //инициализируем строку с результатами операции на данной странице
        String resultString = null;
        try {
            //получаем с форм ввода на странице инфу о пользователе
            String id_user = (String) request.getParameter("id_user"); //параметры только строки
            String idnamegroup = (String) request.getParameter("idnamegroup");
            String username = (String) request.getParameter("username");
            String email = (String) request.getParameter("email");
            String phone = (String) request.getParameter("phone");
            String name = (String) request.getParameter("name");
            String surname = (String) request.getParameter("surname");
            String comment = (String) request.getParameter("comment");

            //отделим id_group и Name от параметра на странице idnamegroup (паттерн: [id_group]:[название группы])
            String group_id = null;
            String groupname = null;
            String splitidname[];
            if (idnamegroup != null) {
                splitidname = idnamegroup.split(":"); //полученный массив используем для создания user
                group_id = splitidname[0];
                groupname = splitidname[1];
            }

            //складываем параметры с формы в коллекцию
            Map<String, String> updateUser = new HashMap<>();
            updateUser.put("id_user", id_user);
            updateUser.put("group_id", group_id);
            updateUser.put("groupname", groupname);
            updateUser.put("username", username);
            updateUser.put("password", null);
            updateUser.put("passwordStr", null);
            updateUser.put("email", email);
            updateUser.put("phone", phone);
            updateUser.put("name", name);
            updateUser.put("surname", surname);
            updateUser.put("comment", comment);

            //применяем изменения к открытому на странице пользователю
            userToUpdate.applyChanges(updateUser);

            //проверка форматов и допустимости введенных данных, генерируем исключение UserException в случае проблем
            userToUpdate.checkUsernamePattern();
            userToUpdate.checkEmailPattern();
            userToUpdate.checkPhonePattern();

            //проверяем параметры измененного пользователя на уникальность в рамках всех пользователей БД, генерируем UserException в случае проблем            
            Connection con = DbConnection.getConnection();
            List<User> allUsers = UserDbQuery.selectUser(con); //все пользователи
            List<User> remUsers = new ArrayList<>();//коллекция пользователей-исключений
            remUsers.add((User) request.getAttribute("user")); //исключить из проверки на уникальность самого редактирумого пользователя, иначе проверку не пройти
            userToUpdate.checkUniqueUser(allUsers, remUsers);//проверка на уникальность пользователя (username, e-mail, phone - не должны совпадать с существующими, исключая редактируемого пользователя)

            //апдетим данные пользователя в БД
            UserDbQuery.updateUser(con, userToUpdate);
            con.close();

            //успешный результат операции запоминаем     
            resultString = "Изменения внесены";

            //если что-то пошло не так, фиксируем другой результат операции
        } catch (SQLException | NamingException | UserException ex) {
            resultString = "Ошибка! " + ex.toString();

            //выводим результат операции на эту же страницу  
        } finally {
            request.setAttribute("resultString", resultString); //передали результат действия на страницу
            request.setAttribute("user", userToUpdate); //сохранили что ввели и передали на страницу для отображения
            dispatcher.forward(request, response); //показали эту же страницу с новыми данными
        }
    }
}
