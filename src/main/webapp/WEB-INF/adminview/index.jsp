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
   
    <h2>События АДМИН</h2>
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
            <c:forEach items = "${allEvents}" var = "allEvents">
                <tr>
                    <td>${allEvents.id_event}</td>
                    <td><a href="viewuser?id_user=${allEvents.author.person.id_user}">${allEvents.author.person.username}</a></td>
                    <td>${allEvents.name}</td>
                    <td>${allEvents.description}</td>
                    <td>${allEvents.place}</td>
                    <td>${allEvents.eventTime}</td>
                    <td>${allEvents.maxParticipants}</td>
                    <td>${allEvents.countOfParticipants}</td>
                    <td>${allEvents.critTime}</td>
                    <td>${allEvents.evStatus.name}</td>
                    <td>${allEvents.evRegStatus.name}</td>
                    <td>${allEvents.createTime}</td>
                    <td><a href = "viewevent?id_event=${allEvents.id_event}">Подробно...</a></td>
                </tr>
            </c:forEach>
            </table> 
    </body>
</html>
