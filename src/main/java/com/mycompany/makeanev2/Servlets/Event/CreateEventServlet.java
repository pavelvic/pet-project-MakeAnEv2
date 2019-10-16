package com.mycompany.makeanev2.Servlets.Event;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация создания меропприятия
URL /createev*/
public class CreateEventServlet extends HttpServlet {
RequestDispatcher dispatcher;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        dispatcher = (RequestDispatcher) request.getAttribute("dispatcher"); //получаем какую страницу открыть (разные группы пользовтеля имею свои страницы со своим интерфейсом и функциями)
        dispatcher.forward(request, response); //открываем страницу
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String resultString = null; //переменная для фиксации результата операции
        Event event = null; //регистрируемое мероприятие пользователь для записи в БД
        
        try {
            //разбираем введенные в форму регистрации параметры из http-запроса
            String name = (String) request.getParameter("name");
            String description = (String) request.getParameter("description");
            String place = (String) request.getParameter("place");
            String parEventTime = (String) request.getParameter("eventTime");
            String maxParticipants = (String) request.getParameter("maxParticipants");
            String parCritTime = (String) request.getParameter("critTime")+ZoneId.ofOffset("", ZoneOffset.UTC);
            
            LocalDateTime eventTime = LocalDateTime.parse(parEventTime);
            ZonedDateTime critTime = ZonedDateTime.parse(parCritTime);
            
            //создаем событие: id -0 (не важно), статус 1 (Планируется, статус регистрации 1(открыта)
            event = new Event(0, 1, 1, name, description, place, eventTime, Integer.parseInt(maxParticipants), ZonedDateTime.now(java.time.Clock.systemUTC()), critTime);

            //проверяем правильно ли введены даты события (дата и время должна быть позже критичной даты)
            event.checkTimes();

            //добавляем запись в БД и устанавливаем сообщение об успехе
            Connection con = DbConnection.getConnection();
            EventDbQuery.insertEvent(con, event);
            con.close();
            resultString = "Мероприятие создано!";
            
            event = null; //обнуляем экземпляр, чтобы на старнице вывелись пустые поля
            
            //если что-то пошло не так, фиксируем ошибку
        } catch (SQLException | NamingException | EventException| NumberFormatException ex) {
            resultString = "Ошибка! " + ex.toString();
                    
        //выводим результат операции
        } finally {
            request.setAttribute("resultString", resultString);
            request.setAttribute("event", event);
            dispatcher.forward(request, response); //идем на страницу в завиимости от dispatcher
        }
    }
    }