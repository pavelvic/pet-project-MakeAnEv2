<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
    <div class="row">
        <div class="col">
            ${namePrevMonth} ${yearPrevMonth}
            <table class="table table-sm">
                <tr>
                    <th scope="col">Пн</th>
                    <th scope="col">Вт</th>
                    <th scope="col">Ср</th>
                    <th scope="col">Чт</th>
                    <th scope="col">Пт</th>
                    <th scope="col">Сб</th>
                    <th scope="col">Вс</th>
                </tr>
                <c:forEach items="${weeksPrevMonth}" var="weeksPrevMonth">
                    <tr>
                        <td>
                            <a href="?day=${weeksPrevMonth.mon}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.mon}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksPrevMonth.tue}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.tue}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksPrevMonth.wed}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.wed}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksPrevMonth.thu}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.thu}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksPrevMonth.fri}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.fri}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksPrevMonth.sat}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.sat}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksPrevMonth.sun}&month=${weeksPrevMonth.month}&year=${weeksPrevMonth.year}">${weeksPrevMonth.sun}</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="col">
            ${nameActualMonth} ${yearActualMonth}
            <table class="table table-sm">
                <tr>
                    <th scope="col">Пн</th>
                    <th scope="col">Вт</th>
                    <th scope="col">Ср</th>
                    <th scope="col">Чт</th>
                    <th scope="col">Пт</th>
                    <th scope="col">Сб</th>
                    <th scope="col">Вс</th>
                </tr>
                <c:forEach items="${weeksActualMonth}" var="weeksActualMonth">
                    <tr>
                        <td>
                            <a href="?day=${weeksActualMonth.mon}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.mon}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksActualMonth.tue}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.tue}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksActualMonth.wed}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.wed}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksActualMonth.thu}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.thu}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksActualMonth.fri}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.fri}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksActualMonth.sat}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.sat}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksActualMonth.sun}&month=${weeksActualMonth.month}&year=${weeksActualMonth.year}">${weeksActualMonth.sun}</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

        <div class="col">
            ${nameNextMonth} ${yearNextMonth}
            <table class="table table-sm">
                <tr>
                    <th scope="col">Пн</th>
                    <th scope="col">Вт</th>
                    <th scope="col">Ср</th>
                    <th scope="col">Чт</th>
                    <th scope="col">Пт</th>
                    <th scope="col">Сб</th>
                    <th scope="col">Вс</th>
                </tr>
                <c:forEach items="${weeksNextMonth}" var="weeksNextMonth">
                    <tr>
                        <td>
                            <a href="?day=${weeksNextMonth.mon}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.mon}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksNextMonth.tue}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.tue}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksNextMonth.wed}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.wed}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksNextMonth.thu}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.thu}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksNextMonth.fri}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.fri}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksNextMonth.sat}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.sat}</a>
                        </td>
                        <td>
                            <a href="?day=${weeksNextMonth.sun}&month=${weeksNextMonth.month}&year=${weeksNextMonth.year}">${weeksNextMonth.sun}</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>