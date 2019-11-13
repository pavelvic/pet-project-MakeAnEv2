<%-- 
    Document   : viewuser
    Created on : 02.03.2019, 20:16:53
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Мероприятие</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <h1>Мероприятие</h1>
            <table border="0">
                <tr>
                    <td>Рег. номер события</td>
                    <td>${event.id_event}</td>
            </tr>

            <tr>
                <td>Статус</td>
                <td>${event.evStatus.name}</td>
            </tr>

            <tr>
                <td>Регистрация</td>
                <td>${event.evRegStatus.name}</td>
            </tr>

            <tr>
                <td>Название</td>
                <td>${event.name}</td>
            </tr>

            <tr>
                <td>Описание</td>
                <td>${event.description}</td>
            </tr>

            <tr>
                <td>Место</td>
                <td>${event.place}</td>
            </tr>

            <tr>
                <td>Дата и время (местн.)</td>
                <td>${event.eventTime}</td>
            </tr>

            <tr>
                <td>Макс. участников</td>
                <td>${event.maxParticipants}</td>
            </tr>

            <tr>
                <td>Критичная дата (местн.)</td>
                <td>${event.critTime}</td>
            </tr>

            <tr>
                <td>Создано</td>
                <td>${event.createTime}</td>
            </tr>
            
            <tr>
                <td>Автор</td>
                <td>${author.person.username}</td>
            </tr>
        </table>
            
            <h2>Участники</h2>
            <table border = "1" cellpadding="5" cellspacing="1">
                <tr>
                    <th>Логин</th>
                    <th>Статус</th>
                    <th>e-mail</th>
                    <th>Тел</th>
                    <th>Имя</th>
                    <th>Фамилия</th>
                    <th>Добавил</th>
                    <th>От</th>                   
                </tr>
            <c:forEach items = "${participants}" var = "participants">
                <tr>
                    <td>${participants.person.username}</td>
                    <td>${participants.status.name}</td>
                    <td>${participants.person.email}</td>
                    <td>${participants.person.phone}</td>
                    <td>${participants.person.name}</td>
                    <td>${participants.person.surname}</td>
                    <td>${participants.whoAdd.username}</td>
                    <td>${participants.regDatetime}</td>
                </tr>
            </c:forEach>
            </table>
    </body>
</html>
