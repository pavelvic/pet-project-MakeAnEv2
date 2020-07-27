<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
            <title>Изменить пароль</title>
        </head>
        <body>
            <div class = "container-fluid">
                <div class = "row">
                    <div class = "col-md-4">
                        <form method="POST" action="">
                        <c:if test="${loginedUser.group_id != 1}">
                            <div class="form-group">
                                <label>Текущий пароль</label>
                                <input type="text" name="actualPassword" class="form-control" value="">
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label>Новый пароль</label>
                            <input type="text" name="newPassword" class="form-control" value="">
                        </div>
                        <input type="submit" value="Изменить" class = "btn btn-info">
                        <a class = "btn btn-info" href=${pageContext.request.contextPath}>Отмена</a>
                    </form>
                    <p class = "text-danger">${resultString}</p>
                </div>
            </div>
        </div>
        <jsp:include page="/_bootstrapJS.jsp"></jsp:include> 
    </body>
</html>