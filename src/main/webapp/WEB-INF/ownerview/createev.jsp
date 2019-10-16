<%-- 
    Document   : createev
    Created on : Oct 13, 2019, 4:54:26 PM
    Author     : Pavelvic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Создать событие</title>
    </head>
    <body>
        <jsp:include page="/_menu.jsp"></jsp:include>
        <h1>Создать событие СУПЕР</h1>
        <h4>${resultString}</h4>
        <form method="POST" action="">
           <table border="0">
            <tr>
               <td>Название</td>
               <td><input type="text" name="name" required="required" value=${event.name}></td>
            </tr>
            
            <tr>
               <td>Описание</td>
               <td><input type="text" name="description" required="required" value=${event.description}></td>
            </tr>
            
            <tr>
               <td>Место</td>
               <td><input type="text" name="place" required="required" value=${event.place}></td>
            </tr>
            
            <tr>
               <td>Дата и время события</td>
               <td><input type="datetime-local" name="eventTime" required="required" value=${event.eventTime}></td>
            </tr>
            
            <tr>
               <td>Макс. число участников</td>
               <td><input type="number" name="maxParticipants" required="required" min ="1" value=${event.maxParticipants}></td>
            </tr>
            
            <tr>
               <td>Критичная дата события</td>
               <td><input type="datetime-local" name="critTime" required="required" value=${event.critTime}></td>
            </tr>
            
            <tr>
               <td colspan="2">
                   <input type="submit" value="Сохранить">
               <a href=${pageContext.request.contextPath}>Назад</a>
                   <input type="reset" value="Очистить">
               </td>
            </tr>
           </table>
        </form>
    </body>
</html>
