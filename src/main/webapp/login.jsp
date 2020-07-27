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
                <div class = "row">
                    <div class = "col-md-4">
                        <h2 class = "text-info">Войти</h2>
                        <p class = "text-danger">${errorString}</p>
                    <form method="POST" action="">
                        <div class="form-group">
                            <label for="exampleInputUsername">Имя пользователя*</label>
                            <input type="text" name="username" class="form-control" id="exampleInputUsername" aria-describedby="UserHelp" value=${user.username}>
                            <small class="form-text text-muted"><a href = "${pageContext.request.contextPath}/register">Нет пользователя? Зарегистрируйтесь!</a></small>
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1">Пароль</label>
                            <input type="password" name="password"  class="form-control" id="exampleInputPassword1" value=${user.passwordStr}>
                        </div>
                        <div class="form-group form-check">
                            <input type="checkbox" name ="rememberMe" class="form-check-input" id="exampleCheck1" value="Y">
                            <label class="form-check-label" for="exampleCheck1">Запомнить</label>
                        </div>
                        <input type="submit" value="Войти" class = "btn btn-info">
                    </form>
                    <jsp:include page="/_bootstrapJS.jsp"></jsp:include>
                </div>
            </div>
        </div>
    </body>
</html>
