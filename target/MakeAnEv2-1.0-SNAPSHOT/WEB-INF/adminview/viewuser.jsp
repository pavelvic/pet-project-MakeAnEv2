<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>

            <title>Профиль</title>
        </head>
        <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <div class = "container-fluid">
                <h2 class = "text-info">Профиль</h2>
                <table border="0">
                    <tr>
                        <td>ID</td>
                        <td>${user.id_user}</td>
                </tr>
                <tr>
                    <td>Имя пользователя</td>
                    <td>${user.username}</td>
                </tr>
                <tr>
                    <td>E-mail</td>
                    <td>${user.email}</td>
                </tr>
                <tr>
                    <td>Группа пользователей</td>
                    <td>${user.groupname}</td>
                </tr>
                <tr>
                    <td>Телефон</td>
                    <td>${user.phone}</td>
                </tr>
                <tr>
                    <td>Имя</td>
                    <td>${user.name}</td>
                </tr>
                <tr>
                    <td>Фамилия</td>
                    <td>${user.surname}</td>
                </tr>
                <tr>
                    <td>Комментарий</td>
                    <td>${user.comment}</td>
                </tr>
            </table>
            <a href = "edituser?id_user=${user.id_user}" class = "btn btn-info">Изменить</a>
            <jsp:include page="/_bootstrapJS.jsp"></jsp:include> 
        </div>
    </body>
</html>
