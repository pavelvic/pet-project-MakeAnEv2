<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
    <title>События</title>
</head>
<body>
<jsp:include page="/_menu.jsp"></jsp:include>
<br>
<div class="container-fluid">
    <div class="table-responsive">
        <table class="table table-striped table-bordered">
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
            </tr>
            <c:forEach items="${likeParticipantEvents}" var="likeParticipantEvents">
                <tr>
                    <td>
                        <a href="viewevent?id_event=${likeParticipantEvents.id_event}">${likeParticipantEvents.id_event}</a>
                    </td>
                    <td><a href="viewevent?id_event=${likeParticipantEvents.id_event}">${likeParticipantEvents.name}</a>
                    </td>
                    <td><a href="viewevent?id_event=${likeParticipantEvents.id_event}">${likeParticipantEvents.description}</a>
                    </td>
                    <td>${likeParticipantEvents.place}</td>
                    <td>${likeParticipantEvents.eventTime}</td>
                    <td>${likeParticipantEvents.maxParticipants}</td>
                    <td>${likeParticipantEvents.countOfParticipants}</td>
                    <td>${likeParticipantEvents.critTime}</td>
                    <td>${likeParticipantEvents.evStatus.name}</td>
                    <td>${likeParticipantEvents.evRegStatus.name}</td>
                    <td>${likeParticipantEvents.createTime}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="/_bootstrapJS.jsp"></jsp:include>
</body>
</html>