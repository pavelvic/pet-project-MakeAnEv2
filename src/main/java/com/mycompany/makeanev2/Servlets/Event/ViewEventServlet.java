package com.mycompany.makeanev2.Servlets.Event;

import com.mycompany.makeanev2.Event;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        Event event = (Event)request.getAttribute("event"); //получаем пользователя для просмотра (сформирован на этапе фильтра и проверен можно ли его смотреть)
        
        
        String formattedEventTime = event.getEventTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        request.setAttribute("eventTime", formattedEventTime);
        
        String formattedCritTime = event.getCritTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        request.setAttribute("critTime", formattedCritTime);
        
        //механизм указания часового пояса позволяет выводить время создания события релевантное для пользователя (в какой временной зоне он находится)
        //с помощью веб-сервиса можно определять временную зону пользователя и передавать в этот параметр
        //аналогично можно сделать зонозависимыми все другие даты события (проведения, критическая дата)
        String formattedCreateTime = event.getCreateTime().withZoneSameInstant(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        request.setAttribute("createTime", formattedCreateTime);
        
        RequestDispatcher dispatcher = (RequestDispatcher) request.getAttribute("dispatcher"); //получаем какую страницу открыть (разные группы пользовтеля имею свои страницы со своим интерфейсом и функциями)
        dispatcher.forward(request, response); //открываем страницу  
    }
}