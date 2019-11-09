package com.mycompany.makeanev2.Servlets.Event;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.Utils.DbConnection;
import com.mycompany.makeanev2.Utils.EventDbQuery;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.ZoneId;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*реализация просмотра информации о мероприятии
URL /viewevent?id_event = ?*/
public class ViewEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ZoneId timeZone = ZoneId.of("Europe/Moscow");
        String errorString;
        try {
            //получаем мероприятие к которому хотим получить доступ
            Connection con = DbConnection.getConnection();
            String id_event = (String) request.getParameter("id_event"); //параметры Object, приводим к String
            Event event = EventDbQuery.selectEvent(con, Integer.parseInt(id_event));
            con.close();

            event.setZone(timeZone);
            //сохраняем в запрос самого пользователя для дальнейшего исопльзования
            request.setAttribute("event", event);
            RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher");
            dispatcher.forward(request, response); //открываем нужную страницу

            //если что-то пошло не так    
        } catch (SQLException | NamingException | NumberFormatException ex) {
            errorString = "Ошибка соединения с базой данных! " + ex.getMessage(); //информация об ошибке
            request.setAttribute("resultString", errorString);
            request.setAttribute("redirect", "/"); //указываем чтобы маршрутизация с resultpage была на главную
            request.getRequestDispatcher("/WEB-INF/resultpage.jsp").forward(request, response); //идем на страницу с ошибкой
        }

//        String formattedEventTime = event.getZonedEventTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
//        request.setAttribute("eventTime", formattedEventTime);
//        
//        String formattedCritTime = event.getCritTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
//        request.setAttribute("critTime", formattedCritTime);
//        
//        //механизм указания часового пояса позволяет выводить время создания события релевантное для пользователя (в какой временной зоне он находится)
//        //с помощью веб-сервиса можно определять временную зону пользователя и передавать в этот параметр
//        //аналогично можно сделать зонозависимыми все другие даты события (проведения, критическая дата)
//        String formattedCreateTime = event.getZonedCreateTime().withZoneSameInstant(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
//        request.setAttribute("createTime", formattedCreateTime);
//        RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher"); //получаем какую страницу открыть (разные группы пользовтеля имею свои страницы со своим интерфейсом и функциями)
//        dispatcher.forward(request, response); //открываем страницу  
    }
}
