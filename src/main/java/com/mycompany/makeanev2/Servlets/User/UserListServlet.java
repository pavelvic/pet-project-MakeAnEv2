package com.mycompany.makeanev2.Servlets.User;

import com.mycompany.makeanev2.User;
import com.mycompany.makeanev2.Utils.UserDbQuery;
import com.mycompany.makeanev2.Utils.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*обработка вывода списка пользователей (все пользователи в БД)
URL /userlist */
public class UserListServlet extends HttpServlet {

    private List<User> list; //контейнер-коллекция для хранения списка пользователей из БД

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errorString; // строка с возможными ошибками
        try {
            Connection con = DbConnection.getConnection();
            list = UserDbQuery.selectUser(con); //получаем список из БД и помещаем в контейнер
            con.close();
            request.setAttribute("userList", list); //передаем коллекцию на страницу для отображения

            //берем из http-запроса инфу какую страницу открывать (определяется с фильтре в зависимости от полномочий)
            //для каждой группы пользователей есть своя страница со списком пользователей для обеспечения возможностей кастомизации
            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу

            //если что-то пошло не так    
        } catch (SQLException | NamingException ex) {
            errorString = "Ошибка соединения с базой данных! " + ex.getMessage(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на главную
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //TODO: Логика поиска и вывода по кнопке
        //один из вариантов реализации поиска
        //получить список для поиска
        //получить что искать (строку)
        //пройти все записи списка и где такая строка находится, оставить, остальные - удалить из list
        //передать на страницу новый список и отобразить
        //подсветить искомую подстроку - опиционально   
    }
}
