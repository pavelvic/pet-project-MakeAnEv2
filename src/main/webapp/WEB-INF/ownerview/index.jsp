<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Главная @ Make events</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
    <jsp:include page="/_menu.jsp"></jsp:include>
   
    <h2>События Супер</h2>
    <jsp:include page="/_searchform.jsp"></jsp:include>
    <jsp:include page="/_monthform.jsp"></jsp:include>
    
            <table border = "1" cellpadding="5" cellspacing="1">
                <tr>
                    <th>Номер</th>
                    <th>Автор</th>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Место</th>
                    <th>Дата и время проведения</th>
                    <th>Макс.чел.</th>
                    <th>Заявились</th>
                    <th>Критичная дата</th>
                    <th>Статус</th>
                    <th>Регистрация</th>
                    <th>Создано</th>
                    <th>Подробно</th>
                </tr>
            <c:forEach items = "${events}" var = "events">
                <tr>
                    <td>${events.id_event}</td>
                    <td><a href="viewuser?id_user=${events.author.person.id_user}">${events.author.person.username}</a></td>
                    <td>${events.name}</td>
                    <td>${events.description}</td>
                    <td>${events.place}</td>
                    <td>${events.eventTime}</td>
                    <td>${events.maxParticipants}</td>
                    <td>${events.countOfParticipants}</td>
                    <td>${events.critTime}</td>
                    <td>${events.evStatus.name}</td>
                    <td>${events.evRegStatus.name}</td>
                    <td>${events.createTime}</td>
                    <td><a href = "viewevent?id_event=${events.id_event}">Подробно...</a></td>
                </tr>
            </c:forEach>
            </table> 
    </body>
</html>
