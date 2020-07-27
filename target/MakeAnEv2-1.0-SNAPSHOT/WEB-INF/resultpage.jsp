<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
            <title>Информация</title>
        </head>
        <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
            <div class = "container-fluid">
                <h1>${resultString}</h1>
            <h3><a href = "${pageContext.request.contextPath}${redirect}">Ok</a></h3>
            <jsp:include page="/_bootstrapJS.jsp"></jsp:include>
        </div>
    </body>
</html>
