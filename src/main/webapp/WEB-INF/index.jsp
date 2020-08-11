<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Главная @ Make events</title>
    <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
</head>
<body>
<jsp:include page="/_menu.jsp"></jsp:include>

<div class="container-fluid">
    <div class="row">
        <div class="col-5">
            <jsp:include page="/_searchform.jsp"></jsp:include>
        </div>
        <div class="col-7">
            <jsp:include page="/_monthform.jsp"></jsp:include>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <p class="text-danger">${resultString}</p>
            <table class="table table-striped table-bordered">
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
                </tr>
                <c:forEach items="${events}" var="events">
                    <tr>
                        <td><a href="viewevent?id_event=${events.id_event}">${events.id_event}</a></td>
                        <td>${events.author.person.username}</td>
                        <td><a href="viewevent?id_event=${events.id_event}">${events.name}</a></td>
                        <td>${events.description}</td>
                        <td>${events.place}</td>
                        <td>${events.eventTime}</td>
                        <td>${events.maxParticipants}</td>
                        <td>${events.countOfParticipants}</td>
                        <td>${events.critTime}</td>
                        <td>${events.evStatus.name}</td>
                        <td>${events.evRegStatus.name}</td>
                        <td>${events.createTime}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<jsp:include page="/_bootstrapJS.jsp"></jsp:include>
</body>
</html>