<%-- 
    Document   : viewuser
    Created on : 02.03.2019, 20:16:53
    Author     : Pavelvic
--%>

<%@page import="com.mycompany.makeanev2.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
           
        </table>
    </body>
</html>
