<%-- 
    Document   : userlist
    Created on : 02.03.2019, 18:12:36
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>События</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <h1>События СУПЕР</h1>
            
            <h2>Участвую</h2>
            <table border = "1" cellpadding="5" cellspacing="1">
                <tr>
                    <th>Номер</th>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Место</th>
                    <th>Дата и время проведения</th>
                    <th>Участников</th>
                    <th>Критичная дата</th>
                    <th>Статус</th>
                    <th>Регистрация</th>
                    <th>Создано</th>
                    <th>Подробно</th>
                </tr>
            <c:forEach items = "${likeParticipantEvents}" var = "likeParticipantEvents">
                <tr>
                    <td>${likeParticipantEvents.id_event}</td>
                    <td>${likeParticipantEvents.name}</td>
                    <td>${likeParticipantEvents.description}</td>
                    <td>${likeParticipantEvents.place}</td>
                    <td>${likeParticipantEvents.eventTime}</td>
                    <td>${likeParticipantEvents.maxParticipants}</td>
                    <td>${likeParticipantEvents.critTime}</td>
                    <td>${likeParticipantEvents.evStatus.name}</td>
                    <td>${likeParticipantEvents.evRegStatus.name}</td>
                    <td>${likeParticipantEvents.createTime}</td>
                    <td><a href = "viewevent?id_event=${likeParticipantEvents.id_event}">Подробно...</a></td>
                </tr>
            </c:forEach>
        </table>
            
            <h2>Организую</h2>
            <table border = "1" cellpadding="5" cellspacing="1">
                <tr>
                    <th>Номер</th>
                    <th>Название</th>
                    <th>Описание</th>
                    <th>Место</th>
                    <th>Дата и время проведения</th>
                    <th>Участников</th>
                    <th>Критичная дата</th>
                    <th>Статус</th>
                    <th>Регистрация</th>
                    <th>Создано</th>
                    <th>Подробно</th>
                </tr>
            <c:forEach items = "${likeAuthorEvents}" var = "likeAuthorEvents">
                <tr>
                    <td>${likeAuthorEvents.id_event}</td>
                    <td>${likeAuthorEvents.name}</td>
                    <td>${likeAuthorEvents.description}</td>
                    <td>${likeAuthorEvents.place}</td>
                    <td>${likeAuthorEvents.eventTime}</td>
                    <td>${likeAuthorEvents.maxParticipants}</td>
                    <td>${likeAuthorEvents.critTime}</td>
                    <td>${likeAuthorEvents.evStatus.name}</td>
                    <td>${likeAuthorEvents.evRegStatus.name}</td>
                    <td>${likeAuthorEvents.createTime}</td>
                    <td><a href = "viewevent?id_event=${likeAuthorEvents.id_event}">Подробно...</a></td>
                </tr>
            </c:forEach>
            </table>         
    </body>
</html>
