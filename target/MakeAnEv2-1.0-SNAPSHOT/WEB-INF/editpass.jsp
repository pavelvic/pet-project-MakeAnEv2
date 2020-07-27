<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
            <title>Изменить пароль</title>
        </head>
        <body>
            <div class = "container-fluid">
                <h1>Изменение пароля</h1>
                <h4>${resultString}</h4>
            <form method="POST" action="">
                <table border="0">
                    <c:if test="${loginedUser.group_id != 1}">
                        <tr>
                            <td>Текущий пароль</td>
                            <td><input type="text" name="actualPassword" value=""></td>
                        </tr>
                    </c:if>
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

            <jsp:include page="/_bootstrapJS.jsp"></jsp:include> 
        </div>
    </body>
</html>
