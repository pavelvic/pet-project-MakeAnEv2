<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
            <title>Профиль</title>
        </head>
        <body>
        <jsp:include page="/_menu.jsp"></jsp:include><br>
            <div class = "container-fluid">           
                <div class = "row">
                    <div class = "col-md-4">
                        <div class="card border-info mb-3">
                            <div class="card-header bg-transparent border-info text-primary">${user.username} (ID: ${user.id_user})</div>
                        <div class="card-body text-primary">
                            <h5 class="card-title">${user.name} ${user.surname}</h5>
                            <p class="card-text">${user.email}</p>
                            <p class="card-text">${user.groupname}</p>
                            <p class="card-text">${user.phone}</p>
                        </div>
                        <div class="card-footer bg-transparent border-info text-muted">${user.comment}</div>
                    </div>
                </div>
            </div>
            <a href = "edituser?id_user=${user.id_user}" class = "btn btn-info">Изменить</a>
        </div>
        <jsp:include page="/_bootstrapJS.jsp"></jsp:include> 
    </body>
</html>
