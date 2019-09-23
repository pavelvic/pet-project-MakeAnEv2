<%-- 
    Document   : login
    Created on : Mar 23, 2019, 3:59:07 PM
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Страница входа</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
        <h1>Введите имя пользователя и пароль</h1>
        <h4>${errorString}</h4>
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
               <td>Запомнить</td>
               <td><input type="checkbox" name ="rememberMe" value="Y"/></td>
            </tr>
            <tr>
               <td colspan="2"><input type="submit" value="Войти">
               <a href=${pageContext.request.contextPath}>Назад</a></td>
            </tr>
           </table>
        </form>
            <h3><a href = "${pageContext.request.contextPath}/register">Регистрация</h3></a>
    </body>
</html>
