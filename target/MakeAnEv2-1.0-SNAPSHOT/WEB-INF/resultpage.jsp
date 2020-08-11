<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/_bootstrapCSS.jsp"></jsp:include>
    <title>Информация</title>
</head>
<body>
<jsp:include page="/_menu.jsp"></jsp:include>
<div class = "container-fluid">
    <p class = "text-info h3">${resultString}</p>
    <a href = "${pageContext.request.contextPath}${redirect}" class = "btn btn-success btn-lg">Хорошо</a>
</div>
<jsp:include page="/_bootstrapJS.jsp"></jsp:include>
</body>
</html>
