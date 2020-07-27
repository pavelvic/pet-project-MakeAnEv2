<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="https://kit.fontawesome.com/32ce59a689.js" crossorigin="anonymous"></script>


=======
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><i class="fas fa-users" style = "color : cadetblue; font-size:55px"></i></a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">

            <c:if test="${loginedUser == null}">
                <li class="nav-item">
                    <a class ="nav-link" href="${pageContext.request.contextPath}/">Главная</a>
                </li>
                <li class="nav-item">
                    <a class ="nav-link" href = "${pageContext.request.contextPath}/register">Регистрация</a>
                </li>
                <li class="nav-item">
                    <a class ="nav-link" href = "${pageContext.request.contextPath}/login">Войти</a>
                </li>
            </ul>
        </c:if>


        <c:if test="${loginedUser.group_id == 1}">

            <li class="nav-item">
                <a class ="nav-link" href="${pageContext.request.contextPath}/">Главная</a>
            </li>
            <li class="nav-item">
                <a class ="nav-link" href = "${pageContext.request.contextPath}/userlist">Пользователи</a>
            </li>

            <li class="nav-item">
                <a class ="nav-link" href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
            </li>
            <li class="nav-item">
                <a class ="nav-link" href = "${pageContext.request.contextPath}/logout">Выйти</a>
            </li>
            </ul>
            <p class="text-right text-muted">
                HELLO, ${loginedUser.username} (${loginedUser.groupname})
            </p>
        </c:if>

        <c:if test="${loginedUser.group_id == 2}">
            <li class="nav-item">
                <a class ="nav-link" href="${pageContext.request.contextPath}/">Главная</a>
            </li>
            <li class="nav-item">
                <a class ="nav-link" href = "${pageContext.request.contextPath}/userlist">Пользователи</a>
            </li>

            <li class="nav-item">
                <a class ="nav-link" href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
            </li>
            <li  class="nav-item">
                <a class ="nav-link" href = "${pageContext.request.contextPath}/logout">Выйти</a>  
            </li>
            </ul>
            <p class="text-right text-muted">
                HELLO, ${loginedUser.username} (${loginedUser.groupname})
            </p>
        </c:if>

        <c:if test="${loginedUser.group_id == 3}">
            <li class="nav-item">
                <a class ="nav-link" href="${pageContext.request.contextPath}/">Главная</a>
            </li>

            <li  class="nav-item">
                <a class ="nav-link" href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
            </li>
            <li class="nav-item">
                <a class ="nav-link" href = "${pageContext.request.contextPath}/logout">Выйти</a>  
            </li>
            </ul>
            <p class="text-right text-muted">
                HELLO, ${loginedUser.username} (${loginedUser.groupname})
            </p>
        </c:if>

        <c:if test="${loginedUser.group_id == 4}">
            <li class="nav-item">
                <a class ="nav-link" href="${pageContext.request.contextPath}/">Главная</a>
            </li>

            <li class="nav-item">
                <a class ="nav-link" href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
            </li>
            <li class="nav-item">
                <a class ="nav-link" href = "${pageContext.request.contextPath}/logout">Выйти</a>  
            </li>
            </ul>
            <p class="text-right text-muted">
                HELLO, ${loginedUser.username} (${loginedUser.groupname})
            </p>
        </c:if>

        <c:if test="${loginedUser.group_id == 5}">
            <li class="nav-item">
                <a class ="nav-link" href="${pageContext.request.contextPath}/">Главная</a>
            </li>

            <li class="nav-item">
                <a class ="nav-link" href="/MakeAnEv2/viewuser?id_user=${loginedUser.id_user}">Мой профиль</a>
            </li>
            <li class="nav-item">
                <a class ="nav-link" href = "${pageContext.request.contextPath}/logout">Выйти</a>  
            </li>
            </ul>
            <p class="text-right text-muted">
                HELLO, ${loginedUser.username} (${loginedUser.groupname})
            </p>
        </c:if>
    </div>
</nav>