<%-- 
    Document   : error
    Created on : 04.03.2019, 0:30:29
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Инфо</title>
    </head>
    <body>
        <jsp:include page="_menu.jsp"></jsp:include>
        <h1>${resultString}</h1>
        <h3><a href = "${pageContext.request.contextPath}${redirect}">Ok</a></h3>
    </body>
</html>
