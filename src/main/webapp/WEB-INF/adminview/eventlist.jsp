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
    <p>
        <a class="btn btn-info" data-toggle="collapse" href="#multiCollapseExample1" role="button" aria-expanded="false"
           aria-controls="multiCollapseExample1">Участвую</a>
        <button class="btn btn-info" type="button" data-toggle="collapse" data-target="#multiCollapseExample2"
                aria-expanded="false" aria-controls="multiCollapseExample2">Организую
        </button>
        <button class="btn btn-info" type="button" data-toggle="collapse" data-target=".multi-collapse"
                aria-expanded="false" aria-controls="multiCollapseExample1 multiCollapseExample2">>>
        </button>
    </p>
    <div class="row container-fluid">
        <div class="collapse multi-collapse" id="multiCollapseExample1">
            <div class="card card-body">
                <h4 class="text-info">Участвую в</h4>
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
                            <td><a href="viewevent?id_event=${likeParticipantEvents.id_event}">${likeParticipantEvents.id_event}</a>
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
    </div>
    <div class="row container-fluid">
        <div class="collapse multi-collapse" id="multiCollapseExample2">
            <div class="card card-body">
                <h4 class="text-info">Организую</h4>
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
                    <c:forEach items="${likeAuthorEvents}" var="likeAuthorEvents">
                        <tr>
                            <td>
                                <a href="viewevent?id_event=${likeAuthorEvents.id_event}">${likeAuthorEvents.id_event}</a>
                            </td>
                            <td><a href="viewevent?id_event=${likeAuthorEvents.id_event}">${likeAuthorEvents.name}</a>
                            </td>
                            <td><a href="viewevent?id_event=${likeAuthorEvents.id_event}">${likeAuthorEvents.description}</a>
                            </td>
                            <td>${likeAuthorEvents.place}</td>
                            <td>${likeAuthorEvents.eventTime}</td>
                            <td>${likeAuthorEvents.maxParticipants}</td>
                            <td>${likeAuthorEvents.countOfParticipants}</td>
                            <td>${likeAuthorEvents.critTime}</td>
                            <td>${likeAuthorEvents.evStatus.name}</td>
                            <td>${likeAuthorEvents.evRegStatus.name}</td>
                            <td>${likeAuthorEvents.createTime}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/_bootstrapJS.jsp"></jsp:include>
</body>
</html>