<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
    <title>Мероприятие</title>
</head>
<body>
<jsp:include page="/_menu.jsp"></jsp:include>
<br>
<div class="container-fluid">
    <div class="row">
        <div class="col-3">
            <div class="card border-info mb-3">
                <div class="card-header bg-transparent border-info text-primary">ID:
                    ${event.id_event}
                </div>
                <div class="card-body text-primary">
                    <h5 class="card-title">${event.name}</h5>
                    <p class="card-text"><small class="form-text text-muted">Статус</small> ${event.evStatus.name}</p>
                    <p class="card-text"><small class="form-text text-muted">Регистрация</small>
                        ${event.evRegStatus.name}</p>
                    <p class="card-text"><small class="form-text text-muted">Что:</small> ${event.description}</p>
                    <p class="card-text"><small class="form-text text-muted">Где:</small> ${event.place}</p>
                    <p class="card-text"><small class="form-text text-muted">Когда:</small> ${event.eventTime}</p>
                    <p class="card-text"><small class="form-text text-muted">Регистрация до</small> ${event.critTime}
                    </p>
                    <p class="card-text"><small class="form-text text-muted">Макс. чел:</small> ${event.maxParticipants}
                    </p>
                </div>
                <div class="card-footer bg-transparent border-info text-muted">Создал <a
                        href="viewuser?id_user=${author.person.id_user}">${author.person.username}</a> от
                    ${event.createTime}
                </div>
            </div>
            <c:if test="${author.person.id_user == loginedUser.id_user}">
                <a class="btn btn-info" href="deleteevent?id_event=${event.id_event}">Удалить</a>
            </c:if>
        </div>
        <div class="col-9">
            <div class="container-fluid">
                <table class="table table-striped table-bordered">
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
                    <c:forEach items="${participants}" var="participants">
                        <tr>
                            <td><a href="viewuser?id_user=${participants.person.id_user}">${participants.person.username}</a>
                            </td>
                            <td>${participants.status.name}</td>
                            <td>${participants.person.email}</td>
                            <td>${participants.person.phone}</td>
                            <td>${participants.person.name}</td>
                            <td>${participants.person.surname}</td>
                            <td><a href="viewuser?id_user=${participants.whoAdd.id_user}">${participants.whoAdd.username}</a>
                            </td>
                            <td>${participants.regDatetime}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <div class="container-fluid">
                <c:if test="${author.person.id_user != loginedUser.id_user}">
                    <c:if test="${regFlag == true}">
                        <a class="btn btn-info" href="subscribe?id_event=${event.id_event}">Участвовать</a>
                    </c:if>
                    <c:if test="${regFlag == false}">
                        <a class="btn btn-info" href="unsubscribe?id_event=${event.id_event}">Отказаться от участия</a>
                    </c:if>
                </c:if>
                <c:if test="${author.person.id_user == loginedUser.id_user}">
                </c:if>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/_bootstrapJS.jsp"></jsp:include>
</body>
</html>     