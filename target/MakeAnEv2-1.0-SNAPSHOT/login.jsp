<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">      
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
        <title>Войти</title>
    </head>
        <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <div class = "container-fluid">
                <h2 class = "text-info">Войти</h2>
                <p class = "text-danger">${errorString}</p>
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
                </table>
                <input type="submit" value="Войти" class = "btn btn-info">
            </form>
            <p class = "text-primary"><a href = "${pageContext.request.contextPath}/register">Нет пользователя? Зарегистрируйтесь!</p></a>
            <jsp:include page="/_bootstrapJS.jsp"></jsp:include>
            </div>
        </body>
</html>
