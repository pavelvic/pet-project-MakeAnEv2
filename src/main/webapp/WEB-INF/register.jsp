<%-- 
    Document   : adduser
    Created on : 02.03.2019, 20:10:01
    Author     : Pavelvic
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Добавить пользователя</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
        <h1>REGISTER HERE</h1>
        <h4>${resultString}</h4>
        <form method="POST" action="">
           <table border="0">
            <tr>
               <td>Имя пользователя*</td>
               <td><input type="text" name="username" value=${user.username}></td>
            </tr>
            <tr>
               <td>Пароль*</td>
               <td><input type="text" name="password" value=${user.passwordStr}></td>
            </tr>           
            <tr>
               <td>E-mail*</td>
               <td><input type="text" name="email" value=${user.email}></td>
            </tr>
            <tr>
               <td>Телефон</td>
               <td><input type="text" name="phone" value=${user.phone}></td>
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
               <a href=${pageContext.request.contextPath}>Назад</a></td>
            </tr>
           </table>
        </form>
    </body>
</html>
