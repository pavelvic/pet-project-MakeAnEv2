<%-- 
    Document   : resetpass
    Created on : Mar 16, 2019, 5:13:39 PM
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Изменить пароль</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
        <h1>Изменение пароля MANAGERVIEW</h1>
        <h4>${resultString}</h4>
        <form method="POST" action="">
           <table border="0">
            <tr>
               <td>Текущий пароль</td>
               <td><input type="text" name="actualPassword" value=""></td>
            </tr>
            <tr>
               <td>Новый пароль</td>
               <td><input type="text" name="newPassword" value=""></td>
            </tr>           

            <tr>
               <td colspan="2"><input type="submit" value="Изменить">
               <a href=${pageContext.request.contextPath}>Назад</a></td>
            </tr>
           </table>
        </form>

    </body>
</html>
