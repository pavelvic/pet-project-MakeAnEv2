<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form method="POST" action="">
    <div class="form-group">
        <small class="form-text text-muted">c</small>
        <input type="date" class="form-control" name="dateFrom" value=${dateFrom}>
        <small class="form-text text-muted">по</small>
        <input type="date" class="form-control" name="dateTo" value=${dateTo}>
        <small class="form-text text-muted">Автор</small>
        <select class="custom-select" name="author" size="1">
            <option selected value="${author_id}:${author_name}">${author_name}</option>

            <c:if test="${author_id != null}">
                <option value=":">Все</option>
            </c:if>

            <c:forEach items="${authors}" var="authors">
                <c:if test="${authors.person.id_user != author_id}">
                    <option value=${authors.person.id_user}:${authors.person.username}>${authors.person.username}
                    </option>
                </c:if>
            </c:forEach>
        </select>
        <small class="form-text text-muted">Статус</small>

        <c:forEach items="${statuses}" var="statuses">
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="checkbox" name="statuses" value="${statuses.id_eventStatus}"
                       ${statuses.checked}>
                <label class="form-check-label">${statuses.name}</label>
            </div>
        </c:forEach>

        <br><small class="form-text text-muted">Быстрый поиск</small>
        <input class="form-control" type="search" name="searchDesc" value="${searchDesc}" placeholder="Я ищу..."><br>
        <div class = "form-group">
            <input class="btn btn-info" type="submit" value="Найти">
            <a href = "${pageContext.request.contextPath}" class = "btn btn-info">Сброс</a>
        </div>
    </div>
</form>