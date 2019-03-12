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
        <jsp:include page="_menu.jsp"></jsp:include>
        <h1>ADD USER HERE</h1>
        <form method="POST" action="">
           <table border="0">
            <tr>
               <td>Имя пользователя*</td>
               <td><input type="text" name="username" value=""></td>
            </tr>
            <tr>
               <td>Пароль*</td>
               <td><input type="text" name="password" value=""></td>
            </tr>           
            <tr>
               <td>E-mail*</td>
               <td><input type="text" name="email" value=""></td>
            </tr>
            <tr>
               <td>Телефон</td>
               <td><input type="text" name="phone" value=""></td>
            </tr>
            <tr>
               <td>Имя</td>
               <td><input type="text" name="name" value=""></td>
            </tr>
            <tr>
               <td>Фамилия</td>
               <td><input type="text" name="surname" value=""></td>
            </tr>
            <tr>
               <td>Комментарий</td>
               <td><input type="text" name="comment" value=""></td>
            </tr>
            <tr>
               <td colspan="2"><input type="submit" value="Submit">
               <a href="${pageContext.request.contextPath}">Cancel</a></td>
            </tr>
           </table>
        </form>
    </body>
</html>
