<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
        <title>События</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <h1>События ПОЛЬЗОВАТЕЛЬ</h1>

            <h2>Участвую</h2>
            <table border = "1" cellpadding="5" cellspacing="1">
                <tr>
                    <th>Номер</th>
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
                    <th>Подробно...</th>
                </tr>
            <c:forEach items = "${likeParticipantEvents}" var = "likeParticipantEvents">
                <tr>
                    <td>${likeParticipantEvents.id_event}</td>
                    <td>${likeParticipantEvents.name}</td>
                    <td>${likeParticipantEvents.description}</td>
                    <td>${likeParticipantEvents.place}</td>
                    <td>${likeParticipantEvents.eventTime}</td>
                    <td>${likeParticipantEvents.maxParticipants}</td>
                    <td>${likeParticipantEvents.countOfParticipants}</td>
                    <td>${likeParticipantEvents.critTime}</td>
                    <td>${likeParticipantEvents.evStatus.name}</td>
                    <td>${likeParticipantEvents.evRegStatus.name}</td>
                    <td>${likeParticipantEvents.createTime}</td>
                    <td><a href = "viewevent?id_event=${likeParticipantEvents.id_event}">Подробно...</a></td>
                </tr>
            </c:forEach>
        </table>
    <jsp:include page="/_bootstrapJS.jsp"></jsp:include>
    </body>
</html>
