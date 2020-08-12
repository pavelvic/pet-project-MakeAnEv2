<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
    <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
    <title>Создать событие</title>
</head>
<body>
<jsp:include page="/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">
            <h2 class="text-info">Новое событие</h1>
                <p class="text-danger">${resultString}</p>
                <form method="POST" action="">
                    <div class="form-group">
                        <label>Название</label>
                        <input type="text" name="name" required="required" class="form-control" placeholder="Что?"
                               value=${event.name}>
                    </div>

                    <div class="form-group">
                        <label>Описание</label>
                        <input type="text" name="description" required="required" class="form-control"
                               placeholder="Что именно?"
                               value=${event.description}>
                    </div>

                    <div class="form-group">
                        <label>Место</label>
                        <input type="text" name="place" required="required" class="form-control" placeholder="Где?"
                               value=${event.place}>
                    </div>

                    <div class="form-group">
                        <label>Когда</label>
                        <input type="datetime-local" name="eventTime" class="form-control" required="required"
                               value=${event.eventTime}>
                    </div>

                    <div class="form-group">
                        <label>Участников, чел.</label>
                        <input type="number" name="maxParticipants" class="form-control" required="required" min="1"
                               value=${event.maxParticipants}>
                    </div>

                    <div class="form-group">
                        <label>Крит. дата</label>
                        <input type="datetime-local" name="critTime" class="form-control" required="required"
                               value=${event.critTime}>
                    </div>

                    <div class="form-group">
                        <label>Статус рег.</label>
                        <select name="id_eventregstatus" size="1" required="required" class="form-control">
                            <c:forEach items="${EventRegStatus}" var="EventRegStatus">
                                <option value=${EventRegStatus.id_eventRegStatus}>${EventRegStatus.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <input type="submit" value="Сохранить" class="btn btn-info">
                    <a class="btn btn-info" href=${pageContext.request.contextPath}>Назад</a>
                    <input class="btn btn-info" type="reset" value="Очистить">
                </form>
                <jsp:include page="/_bootstrapJS.jsp"></jsp:include>
        </div>
    </div>
</div>
<jsp:include page="/_bootstrapJS.jsp"></jsp:include>
</body>
</html>