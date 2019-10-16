<%-- 
    Document   : _menu
    Created on : 07.03.2019, 0:02:22
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${loginedUser == null}">
<div>
    <a href="${pageContext.request.contextPath}/">Главная</a>
    |
    <a href = "${pageContext.request.contextPath}/register">Регистрация</a>
    |
    <a href = "${pageContext.request.contextPath}/login">Войти</a>
</div>
    </c:if>


<c:if test="${loginedUser.group_id == 1}">
<div>
    <a href="${pageContext.request.contextPath}/">Главная</a>
    |
    <a href = "${pageContext.request.contextPath}/createev">Создать событие</a>
    |
    <a href = "${pageContext.request.contextPath}/userlist">Пользователи</a>
    |
    <a href = "${pageContext.request.contextPath}/logout">Выйти</a>
    |
    <a href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
    <div>
    HELLO, ${loginedUser.username} (${loginedUser.groupname})
    </div>
</div>
</c:if>

<c:if test="${loginedUser.group_id == 2}">
<div>
    <a href="${pageContext.request.contextPath}/">Главная</a>
    |
    <a href = "${pageContext.request.contextPath}/createev">Создать событие</a>
    |
    <a href = "${pageContext.request.contextPath}/userlist">Пользователи</a>
    |
    <a href = "${pageContext.request.contextPath}/logout">Выйти</a>  
    |
    <a href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
    <div>
    HELLO, ${loginedUser.username} (${loginedUser.groupname})
    </div>
</div>
</c:if>

<c:if test="${loginedUser.group_id == 3}">
<div>
    <a href="${pageContext.request.contextPath}/">Главная</a>
    |
    <a href = "${pageContext.request.contextPath}/logout">Выйти</a>  
    |
    <a href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
    <div>
    HELLO, ${loginedUser.username} (${loginedUser.groupname})
    </div>
</div>
</c:if>

<c:if test="${loginedUser.group_id == 4}">
<div>
    <a href="${pageContext.request.contextPath}/">Главная</a>
    |
    <a href = "${pageContext.request.contextPath}/logout">Выйти</a>  
    |
    <a href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
    <div>
    HELLO, ${loginedUser.username} (${loginedUser.groupname})
    </div>
</div>
</c:if>

<c:if test="${loginedUser.group_id == 5}">
<div>
    <a href="${pageContext.request.contextPath}/">Главная</a>
    |
    <a href = "${pageContext.request.contextPath}/logout">Выйти</a>  
    |
    <a href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
    <div>
    HELLO, ${loginedUser.username} (${loginedUser.groupname})
    </div>
</div>
</c:if>
