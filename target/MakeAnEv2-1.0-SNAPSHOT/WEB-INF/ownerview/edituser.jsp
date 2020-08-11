<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
    <title>Редактирование пользователя</title>
</head>
<body>
<jsp:include page="/_menu.jsp"></jsp:include>
<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">
            <form method="POST" action="">
                <div class="form-group">
                    <label>Имя пользователя*</label>
                    <input type="text" name="username" class="form-control" value=${user.username}>
                </div>
                <div class="form-group">
                    <label>E-mail*</label>
                    <input type="email" name="email" class="form-control" value=${user.email}>
                </div>
                <div class="form-group">
                    <label>Группа*</label>
                    <select name="idnamegroup" class="form-control">
                        <option selected value=${user.group_id}:${user.groupname}><b>${user.groupname}</b></option>
                        <c:forEach items="${usergroups}" var="usergroups">
                            <c:if test="${usergroups.id_group != user.group_id}">
                                <option value=${usergroups.id_group}:${usergroups.name}>${usergroups.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label>Телефон</label>
                    <input type="tel" name="phone" class="form-control" value=${user.phone}>
                </div>
                <div class="form-group">
                    <label>Имя</label>
                    <input type="text" name="name" class="form-control" value=${user.name}>
                </div>
                <div class="form-group">
                    <label>Фамилия</label>
                    <input type="text" name="surname" class="form-control" value=${user.surname}>
                </div>
                <div class="form-group">
                    <label>Комментарий</label>
                    <input type="text" name="comment" class="form-control" value=${user.comment}>
                </div>
                <input type="submit" value="Сохранить" class="btn btn-info">
                <a class="btn btn-info" href="viewuser?id_user=${user.id_user}">Отмена</a>
                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                        aria-expanded="false">Действия
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="editpass?id_user=${user.id_user}">Изменить пароль</a>
                    <a class="dropdown-item" href="resetpass?id_user=${user.id_user}">Сбросить пароль</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="deleteuser?id_user=${user.id_user}">Удалить пользователя</a>
                </div>
            </form>
            <p class="text-danger">${resultString}</p>
        </div>
    </div>
</div>
<jsp:include page="/_bootstrapJS.jsp"></jsp:include>
</body>
</html>