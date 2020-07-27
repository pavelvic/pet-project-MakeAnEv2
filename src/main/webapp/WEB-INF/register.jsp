<%-- 
    Document   : adduser
    Created on : 02.03.2019, 20:10:01
    Author     : Pavelvic
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
            <title>Регистрация</title>
        </head>
        <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <div class = "container-fluid">
                <div class = "row">
                    <div class = "col-md-4">
                        <h2 class = "text-info">Регистрация</h1>
                            <p class = "text-danger">${resultString}</p>
                        <form method="POST" action="">

                            <div class="form-group">
                                <label>Имя пользователя*</label>
                                <input type="text" name="username" class="form-control" placeholder="username" value=${user.username}>
                            </div>

                            <div class="form-group">
                                <label>Пароль*</label>
                                <input type="password" name="password" class="form-control" placeholder="password" value=${user.passwordStr}>
                            </div>

                            <div class="form-group">
                                <label>E-mail*</label>
                                <input type="email" name="email" class="form-control" placeholder="example@example.com" value=${user.email}>
                            </div>

                            <div class="form-group">
                                <label>Телефон</label>
                                <input type="tel" name="phone"  class="form-control" placeholder="+7(123) 456-78-90" value=${user.phone}>
                            </div>

                            <div class="form-group">
                                <label>Имя</label>
                                <input type="text" name="name"  class="form-control" placeholder="Иван" value=${user.name}>
                            </div>

                            <div class="form-group">
                                <label>Фамилия</label>
                                <input type="text" name="surname"  class="form-control" placeholder="Иванов" value=${user.surname}>
                            </div>
                            <input type="submit" value="Подтвердить" class = "btn btn-info">
                            <input type="reset" value="Очистить" class = "btn btn-info">
                        </form>
                        <jsp:include page="/_bootstrapJS.jsp"></jsp:include>
                </div>
            </div>
        </div>
    </body>
</html>