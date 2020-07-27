<%-- 
    Document   : adduser
    Created on : 02.03.2019, 20:10:01
    Author     : Pavelvic
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
            <title>Регистрация</title>
        </head>
        <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <h2 class = "text-info">Регистрация</h1>
            <p class = "text-danger">${resultString}</p>
        <form method="POST" action="">
            <table border="0">
                <tr>
                    <td>Имя пользователя*</td>
                    <td><input type="text" name="username" value=${user.username}></td>
                </tr>
                <tr>
                    <td>Пароль*</td>
                    <td><input type="password" name="password" value=${user.passwordStr}></td>
                </tr>           
                <tr>
                    <td>E-mail*</td>
                    <td><input type="email" name="email" value=${user.email}></td>
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
            </table>
            <input type="submit" value="Сохранить" class = "btn btn-info">
            <input type="reset" value="Очистить" class = "btn btn-info">
        </form>
        <jsp:include page="/_bootstrapJS.jsp"></jsp:include>
</body>
</html>
