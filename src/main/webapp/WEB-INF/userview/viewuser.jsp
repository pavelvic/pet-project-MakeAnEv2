<%-- 
    Document   : viewuser
    Created on : 02.03.2019, 20:16:53
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Посмотреть пользователя</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
        <h1>Подробная информация о пользователе КАК ПОЛЬЗОВАТЕЛЬ!!!</h1>
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
            <h3><a href = "edituser?id_user=${user.id_user}">ИЗМЕНИТЬ</a></h3>
        <h3><a href = "deleteuser?id_user=${user.id_user}">УДАЛИТЬ</a></h3>
        <h3>НАЗАД</h3>
    </body>
</html>
