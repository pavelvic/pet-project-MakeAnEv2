<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table>
    <tr valign="top">
        <td>
            <div class="prevMonth">
                <table border = "1" cellpadding="5" cellspacing="1">
                    <tr align="center"><b>${namePrevMonth} ${yearPrevMonth}</b></tr>
                    <tr>
                        <th>Пн</th>
                        <th>Вт</th>
                        <th>Ср</th>
                        <th>Чт</th>
                        <th>Пт</th>
                        <th>Сб</th>
                        <th>Вс</th>
                    </tr>
                    <c:forEach items = "${weeksPrevMonth}" var = "weeksPrevMonth">
                        <tr>
                            <td><a href = "?day=${weeksPrevMonth.mon}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.mon}</a></td>
                            <td><a href = "?day=${weeksPrevMonth.tue}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.tue}</a></td>
                            <td><a href = "?day=${weeksPrevMonth.wed}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.wed}</a></td>
                            <td><a href = "?day=${weeksPrevMonth.thu}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.thu}</a></td>
                            <td><a href = "?day=${weeksPrevMonth.fri}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.fri}</a></td>
                            <td><a href = "?day=${weeksPrevMonth.sat}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.sat}</a></td>
                            <td><a href = "?day=${weeksPrevMonth.sun}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.sun}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </td>

        <td>
            <div class="actualMonth">    
                <table border = "1" cellpadding="5" cellspacing="1">
                    <tr align="center"><b>${nameActualMonth} ${yearActualMonth}</b></tr>
                    <tr>
                        <th>Пн</th>
                        <th>Вт</th>
                        <th>Ср</th>
                        <th>Чт</th>
                        <th>Пт</th>
                        <th>Сб</th>
                        <th>Вс</th>
                    </tr>
                    <c:forEach items = "${weeksActualMonth}" var = "weeksActualMonth">
                        <tr>
                            <td><a href = "?day=${weeksActualMonth.mon}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.mon}</a></td>
                            <td><a href = "?day=${weeksActualMonth.tue}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.tue}</a></td>
                            <td><a href = "?day=${weeksActualMonth.wed}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.wed}</a></td>
                            <td><a href = "?day=${weeksActualMonth.thu}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.thu}</a></td>
                            <td><a href = "?day=${weeksActualMonth.fri}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.fri}</a></td>
                            <td><a href = "?day=${weeksActualMonth.sat}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.sat}</a></td>
                            <td><a href = "?day=${weeksActualMonth.sun}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.sun}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </td>

        <td>
            <div class="nextMonth">    
                <table border = "1" cellpadding="5" cellspacing="1">
                    <tr align="center"><b>${nameNextMonth} ${yearNextMonth}</b></tr>
                    <tr>
                        <th>Пн</th>
                        <th>Вт</th>
                        <th>Ср</th>
                        <th>Чт</th>
                        <th>Пт</th>
                        <th>Сб</th>
                        <th>Вс</th>
                    </tr>
                    <c:forEach items = "${weeksNextMonth}" var = "weeksNextMonth">
                        <tr>
                            <td><a href = "?day=${weeksNextMonth.mon}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.mon}</a></td>
                            <td><a href = "?day=${weeksNextMonth.tue}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.tue}</a></td>
                            <td><a href = "?day=${weeksNextMonth.wed}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.wed}</a></td>
                            <td><a href = "?day=${weeksNextMonth.thu}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.thu}</a></td>
                            <td><a href = "?day=${weeksNextMonth.fri}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.fri}</a></td>
                            <td><a href = "?day=${weeksNextMonth.sat}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.sat}</a></td>
                            <td><a href = "?day=${weeksNextMonth.sun}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.sun}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </td>
    </tr>
</table>