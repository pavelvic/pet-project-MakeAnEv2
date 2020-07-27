<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
            <title>Список пользователей</title>
        </head>
        <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <div class = "container-fluid">
                <h2 class = "text-info">Список пользователей</h2>
                <div class = "table-responsive">  
                    <table class = "table table-striped table-bordered">
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
                            <td><a href = "viewuser?id_user=${user.id_user}" 
                                   class = "btn btn-info">Подробно</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    <jsp:include page="/_bootstrapJS.jsp"></jsp:include>
    </body>
</html>
