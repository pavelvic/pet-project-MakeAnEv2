<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Редактирование пользователя</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <div class = "container-fluid">
                <h1>Изменить</h1>
                <h4>${resultString}</h4>
            <form method="POST" action="">
                <table border="0">
                    <tr>
                        <td>ID</td>
                        <td>${user.id_user}</td>
                    </tr>
                    <tr>
                        <td>Имя пользователя*</td>
                        <td>${user.username}</td>
                    </tr>
                    <tr>
                        <td>E-mail*</td>
                        <td>${user.email}</td>
                    </tr>

                    <tr>
                        <td>Группа*</td>
                        <td>${user.groupname}</td>
                    </tr>
                    <tr>
                        <td>Телефон</td>
                        <td><input type="tel" name="phone" value=${user.phone}></td>
                    </tr>
                    <tr>
                        <td>Имя</td>
                        <td><input type="text" name="name" value=${user.name}></td>
                    </tr>
                    <tr>
                        <td>Фамилия</td>
                        <td><input type="text" name="surname" value=${user.surname}></td>
                    </tr>
                    <tr>
                        <td>Комментарий</td>
                        <td><input type="text" name="comment" value=${user.comment}></td>
                    </tr>
                    <tr>
                        <td colspan="2"><input type="submit" value="Сохранить">
                        </td>
                    </tr>
                </table>
                <h3> <a href="viewuser?id_user=${user.id_user}">Отмена</a></h3>
            </form>
            <h3><a href = "editpass?id_user=${user.id_user}">Изменить пароль</a></h3>
        </div>
    </body>
</html>
