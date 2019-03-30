<%-- 
    Document   : userlist
    Created on : 02.03.2019, 18:12:36
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Список пользователей</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <h1>Список пользователей</h1>
        <table border = "1" cellpadding="5" cellspacing="1">
            <tr>
                <th>ID</th>
                <th>Логин</th>
                <th>E-mail</th>
                <th>Группа польз.</th>

                <th>Подробно</th>
            </tr>
        <c:forEach items = "${userList}" var = "user">
            <tr>
                <td>${user.id_user}</td>
                <td>${user.username}</td>
                <td>${user.email}</td>
                <td>${user.groupname}</td>

                <td><a href = "viewuser?id_user=${user.id_user}">Подробно...</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="adduser" >Создать пользователя</a>
</body>
</html>
