<%-- 
    Document   : index
    Created on : Mar 23, 2019, 11:51:47 PM
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Главная @ Make events</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <div>HELLO, ${loginedUser.username} (${loginedUser.groupname}), <a href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">[Мои данные]</a></div>
        <div><a href="/MakeAnEv2/userlist">Список пользователей</a></div>
        <div><a href="/MakeAnEv2/register">Регистрация</a></div>
        <div><a href="/MakeAnEv2/login">Войти</a></div>
        <div><a href="/MakeAnEv2/logout">Выйти</a></div>
        
    </body>
</html>
