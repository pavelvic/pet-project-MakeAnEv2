<%-- 
    Document   : edituser
    Created on : 08.03.2019, 23:25:50
    Author     : Pavelvic
--%>

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
        <h1>EDIT USER HERE КАК СУПЕР"!!</h1>
        <h4>${resultString}</h4>
        <form method="POST" action="">
           <table border="0">
            <tr>
              <td>ID</td>
              <td>${user.id_user}</td>
            </tr>
            <tr>
               <td>Имя пользователя*</td>
               <td><input type="text" name="username" value=${user.username}></td>
            </tr>
            <tr>
               <td>E-mail*</td>
               <td><input type="text" name="email" value=${user.email}></td>
            </tr>
            
            <tr>
               <td>Группа*</td>
               <td>
                <select name ="idnamegroup" size="1">
                    <option selected value=${user.group_id}:${user.groupname}><b>${user.groupname}</b></option>
                    <c:forEach items = "${usergroups}" var = "usergroups">
                        <c:if test="${usergroups.id_group != user.group_id}">
                            <option value=${usergroups.id_group}:${usergroups.name}>${usergroups.name}</option>
                        </c:if>
                    </c:forEach> 
               </td>
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
               <a href="viewuser?id_user=${user.id_user}">Назад</a></td>
            </tr>
           </table>
        </form>
        <h3><a href = "editpass?id_user=${user.id_user}">Изменить пароль</a></h3>
        <h3><a href = "resetpass?id_user=${user.id_user}">Сбросить пароль</a></h3>
    </body>
</html>
