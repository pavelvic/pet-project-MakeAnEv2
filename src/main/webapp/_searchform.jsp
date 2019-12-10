<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

    <form method="POST" action="">
    <h4>с <input type="date" name="dateFrom" value=${dateFrom}>  по <input type="date" name="dateTo" value=${dateTo}></h4>
    <h4> Автор 
        <select name ="author" size="1">
            <option selected value="${author_id}:${author_name}">${author_name}</option>  
            
                    <c:if test="${author_id != null}">
                            <option value=":">Все</option> 
                    </c:if>
            
                    <c:forEach items = "${authors}" var = "authors">
                        <c:if test="${authors.person.id_user != author_id}">
                            <option value=${authors.person.id_user}:${authors.person.username}>${authors.person.username}</option> 
                        </c:if>
                    </c:forEach> 
        </select>
    </h4>
    
    <p>

            <c:forEach items = "${statuses}" var = "statuses">
                <p><input type="checkbox" name="statuses" value="${statuses.id_eventStatus}" ${statuses.checked}>${statuses.name}</p>
            </c:forEach>
                      
    </p>
    
    
    <h4>Описание <input type="search" size ="31" name="searchDesc" value="${searchDesc}"></h4>
    <h4><input type="submit" value="Найти"></h4>
    </form>