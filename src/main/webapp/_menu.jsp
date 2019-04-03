<%-- 
    Document   : _menu
    Created on : 07.03.2019, 0:02:22
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div>
   <a href="${pageContext.request.contextPath}/">Главная</a>
   |
   <a href="${pageContext.request.contextPath}/userlist">Список пользователей</a>
   |
   <a href="${pageContext.request.contextPath}/adduser">Добавить пользователя</a>
   |
   <a href = "${pageContext.request.contextPath}/login">Войти</a>
   |
   <a href = "${pageContext.request.contextPath}/logout">Выйти</a>
   |
   HELLO, ${loginedUser.username} (${loginedUser.groupname})

</div>
