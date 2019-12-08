package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.EventRegStatus;
import com.mycompany.makeanev2.EventStatus;
import com.mycompany.makeanev2.Exceptions.EventException;
import com.mycompany.makeanev2.Exceptions.SearchException;
import com.mycompany.makeanev2.Participant;
import com.mycompany.makeanev2.ParticipantStatus;
import com.mycompany.makeanev2.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventDbQuery {

    public static void insertEvent(Connection con, Event ev) throws SQLException {

        //в подходах java определено использовать символ "?" как параметр sql запроса
        String sql = "INSERT INTO event(id_event, eventstatus_id, eventregstatus_id, name, description, place, eventTime, maxParticipants, createTime, critTime) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        //здесь определяем параметры (символ "?") для sql-запроса на основе объекта
        ps.setInt(1, ev.getId_event());
        ps.setInt(2, ev.getEvStatus().getId_eventStatus());
        ps.setInt(3, ev.getEvRegStatus().getId_eventRegStatus());
        ps.setString(4, ev.getName());
        ps.setString(5, ev.getDescription());
        ps.setString(6, ev.getPlace());

        ps.setLong(7, ev.getZonedEventTime().toEpochSecond());

        ps.setInt(8, ev.getMaxParticipants());

        //аналогично для другого поля с датой
        ps.setLong(9, ev.getZonedCreateTime().toEpochSecond());
        ps.setLong(10, ev.getZonedCritTime().toEpochSecond());

        //ВЫПОЛНЯЕМ sql-запрос
        ps.executeUpdate();
    }

    public static Event selectEvent(Connection con, int id_event) throws SQLException, EventException {

        String sql = "SELECT e.id_event, es.id_eventstatus, es.name, ers.id_eventregstatus, ers.name, e.name, e.description, e.place, e.eventTime, e.maxParticipants, e.createTime, e.critTime "
                + "FROM `event` e, `eventregstatus` ers, `eventstatus` es "
                + "WHERE e.eventregstatus_id = ers.id_eventregstatus "
                + "AND e.eventstatus_id = es.id_eventstatus "
                + "AND e.id_event = ?";

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, id_event);

        ResultSet rs = ptsm.executeQuery();

        if (rs.next()) {
            return new Event(rs);
        }
        throw new EventException("Запрошенного события не существует");
        //return null;
    }

    public static List<EventRegStatus> selectEventStatuses(Connection con) throws SQLException {
        String sql = "SELECT id_eventregstatus, name FROM eventregstatus ORDER BY id_eventregstatus ASC";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();
        List<EventRegStatus> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new EventRegStatus(rs));
        }
        return list;
    }

    public static int selectMaxEventId(Connection con) throws SQLException {
        String sql = "SELECT MAX(id_event) FROM event";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }

        return 0;
    }

    public static EventRegStatus selectEventRegStatusById(Connection con, int id_eventRegStatus) throws SQLException {

        String sql = "SELECT id_eventregstatus, name "
                + "FROM `eventregstatus` "
                + "WHERE id_eventregstatus = ?";

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, id_eventRegStatus);

        ResultSet rs = ptsm.executeQuery();

        if (rs.next()) {
            return new EventRegStatus(rs);
        }
        return null;
    }

    public static void insertParticipant(Connection con, Participant pct, Event ev) throws SQLException {

        String sql = "INSERT INTO participant(event_id, user_id, participantStatus_id, user_id_whoadd, isAuthor, regDatetime) VALUES (?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, ev.getId_event());
        ps.setInt(2, pct.getPerson().getId_user());
        ps.setInt(3, pct.getStatus().getId_participantStatus());
        ps.setInt(4, pct.getWhoAdd().getId_user());
        ps.setBoolean(5, pct.getAuthor());
        ps.setLong(6, pct.getZonedRegDatetime().toEpochSecond());

        //ВЫПОЛНЯЕМ sql-запрос
        ps.executeUpdate();
    }

    public static void deleteParticipant(Connection con, Participant pct) throws SQLException {

        String sql = "DELETE FROM participant WHERE id_participant = ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, pct.getId_participant());

        //ВЫПОЛНЯЕМ sql-запрос
        ps.executeUpdate();
    }

    public static List<Event> selectEventsByUserLikeAuthor(Connection con, int id_user) throws SQLException {

        String sql = "SELECT e.id_event, es.id_eventstatus, es.name, ers.id_eventregstatus, ers.name, e.name, e.description, e.place, e.eventTime, e.maxParticipants, e.createTime, e.critTime "
                + "FROM `event` e, `eventregstatus` ers, `eventstatus` es, `participant` p "
                + "WHERE e.eventregstatus_id = ers.id_eventregstatus "
                + "AND e.eventstatus_id = es.id_eventstatus "
                + "AND e.id_event = p.event_id "
                + "AND p.user_id = ? "
                + "AND p.isAuthor = 1";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id_user);

        ResultSet rs = pstm.executeQuery();
        List<Event> list = new ArrayList<>();

        while (rs.next()) {

            Event ev = new Event(rs);
            List<Participant> participantList = EventDbQuery.selectParticipantsOfEvent(con, ev);
            ev.setParticipants(participantList);
            list.add(ev);
        }
        return list;
    }

    public static List<Event> selectEventsByUserLikeParticipant(Connection con, int id_user) throws SQLException {

        String sql = "SELECT e.id_event, es.id_eventstatus, es.name, ers.id_eventregstatus, ers.name, e.name, e.description, e.place, e.eventTime, e.maxParticipants, e.createTime, e.critTime "
                + "FROM `event` e, `eventregstatus` ers, `eventstatus` es, `participant` p "
                + "WHERE e.eventregstatus_id = ers.id_eventregstatus "
                + "AND e.eventstatus_id = es.id_eventstatus "
                + "AND e.id_event = p.event_id "
                + "AND p.user_id = ? "
                + "AND p.isAuthor = 0";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id_user);

        ResultSet rs = pstm.executeQuery();
        List<Event> list = new ArrayList<>();

        while (rs.next()) {

            Event ev = new Event(rs);
            List<Participant> participantList = EventDbQuery.selectParticipantsOfEvent(con, ev);
            ev.setParticipants(participantList);
            list.add(ev);
        }
        return list;
    }

    public static List<Event> selectAllEvents(Connection con) throws SQLException {

        String sql = "SELECT e.id_event, es.id_eventstatus, es.name, ers.id_eventregstatus, ers.name, e.name, e.description, e.place, e.eventTime, e.maxParticipants, e.createTime, e.critTime "
                + "FROM `event` e, `eventregstatus` ers, `eventstatus` es "
                + "WHERE e.eventregstatus_id = ers.id_eventregstatus "
                + "AND e.eventstatus_id = es.id_eventstatus";

        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();
        List<Event> list = new ArrayList<>();

        while (rs.next()) {

            Event ev = new Event(rs);
            List<Participant> participantList = EventDbQuery.selectParticipantsOfEvent(con, ev);
            ev.setParticipants(participantList);
            list.add(ev);
        }
        return list;
    }

    public static List<Event> selectEventsFromDateExceptStatus(Connection con, ZonedDateTime fromDate, EventStatus es) throws SQLException {

        String sql = "SELECT e.id_event, es.id_eventstatus, es.name, ers.id_eventregstatus, ers.name, e.name, e.description, e.place, e.eventTime, e.maxParticipants, e.createTime, e.critTime "
                + "FROM `event` e, `eventregstatus` ers, `eventstatus` es "
                + "WHERE e.eventregstatus_id = ers.id_eventregstatus "
                + "AND e.eventstatus_id = es.id_eventstatus "
                + "AND e.eventTime >= ? "
                + "AND es.id_eventstatus <> ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setLong(1, fromDate.toEpochSecond());
        pstm.setInt(2, es.getId_eventStatus());

        ResultSet rs = pstm.executeQuery();
        List<Event> list = new ArrayList<>();

        while (rs.next()) {

            Event ev = new Event(rs);
            List<Participant> participantList = EventDbQuery.selectParticipantsOfEvent(con, ev);
            ev.setParticipants(participantList);
            list.add(ev);
        }
        return list;
    }

    public static List<Participant> selectParticipantsOfEvent(Connection con, Event ev) throws SQLException {

        User person;
        ParticipantStatus status;
        User whoAdd;
        ZonedDateTime regDateTime;

        String sql = "SELECT p.id_participant id_partic, pers.id_user pers_id_user, pers.group_id pers_group_id, ugpers.Name pers_groupname, pers.username pers_username, pers.password pers_password, pers.email pers_email, pers.phone pers_phone, pers.name pers_name, pers.surname pers_surname, pers.comment pers_comment, "
                + "whoadd.id_user whoadd_id_user, whoadd.group_id whoadd_group_id, ugwhoadd.Name whoadd_groupname, whoadd.username whoadd_username, whoadd.password whoadd_password, whoadd.email whoadd_email, whoadd.phone whoadd_phone, whoadd.name whoadd_name, whoadd.surname whoadd_surname, whoadd.comment whoadd_comment, "
                + "p.participantStatus_id participantStatus_id, ps.name status_name, "
                + "p.regDatetime regDatetime, p.isAuthor isAuthor "
                + "FROM participant p "
                + "LEFT JOIN user pers ON p.user_id = pers.id_user "
                + "LEFT JOIN usergroups ugpers ON ugpers.id_group = pers.group_id "
                + "LEFT JOIN user whoadd ON p.user_id_whoadd = whoadd.id_user "
                + "LEFT JOIN usergroups ugwhoadd ON ugwhoadd.id_group = whoadd.group_id "
                + "LEFT JOIN participantstatus ps ON p.participantStatus_id = ps.id_participantstatus "
                + "WHERE p.event_id = ?";

        //план такой:сделать запрос, собрать из него все необходимые объекты, собрать целевой объект, записать в коллекцию
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, ev.getId_event());

        ResultSet rs = pstm.executeQuery();
        List<Participant> list = new ArrayList<>();

        while (rs.next()) {

            //создаем person
            person = new User(rs.getInt("pers_id_user"),
                    rs.getInt("pers_group_id"),
                    rs.getString("pers_groupname"),
                    rs.getString("pers_username"),
                    rs.getInt("pers_password"),
                    "",
                    rs.getString("pers_email"),
                    rs.getString("pers_phone"),
                    rs.getString("pers_name"),
                    rs.getString("pers_surname"),
                    rs.getString("pers_comment"));

            status = new ParticipantStatus(rs.getInt("participantStatus_id"), rs.getString("status_name"));

            whoAdd = new User(rs.getInt("whoadd_id_user"),
                    rs.getInt("whoadd_group_id"),
                    rs.getString("whoadd_groupname"),
                    rs.getString("whoadd_username"),
                    rs.getInt("whoadd_password"),
                    "",
                    rs.getString("whoadd_email"),
                    rs.getString("whoadd_phone"),
                    rs.getString("whoadd_name"),
                    rs.getString("whoadd_surname"),
                    rs.getString("whoadd_comment"));

            regDateTime = ZonedDateTime.of(LocalDateTime.ofEpochSecond(rs.getLong("regDatetime"), 0, ZoneOffset.UTC), ZoneId.of("UTC"));

            list.add(new Participant(rs.getInt("id_partic"), person, status, whoAdd, rs.getInt("isAuthor"), regDateTime));
        }
        return list;
    }

    public static List<Participant> selectAllAuthors(Connection con) throws SQLException {

        String sql = "SELECT DISTINCT u.id_user, u.group_id, g.Name, u.username, u.password, u.email, u.phone, u.name, u.surname, u.comment FROM participant p, user u, usergroups g "
                + "WHERE p.user_id = u.id_user "
                + "AND u.group_id = g.id_group "
                + "AND isAuthor = 1 "
                + "ORDER BY u.username";

        //план такой:сделать запрос, собрать из него все необходимые объекты, собрать целевой объект, записать в коллекцию
        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet rs = pstm.executeQuery();
        List<Participant> list = new ArrayList<>();

        while (rs.next()) {
            list.add(new Participant(new User(rs), null, null, null));
        }
        return list;
    }

    //реализация поиска сербокса
    public static List<Event> selectEventsByParam(Connection con, LocalDateTime dateFrom, LocalDateTime dateTo, int id_author, String searchDesc) throws SQLException, SearchException {

        //признак для формирования параметров запроса: d - ищем запрос сожержит даты, a - автора, t - текст из текстовых полей события
        //базовый запрос
        String sql = "SELECT e.id_event, es.id_eventstatus, es.name, ers.id_eventregstatus, ers.name, e.name, e.description, e.place, e.eventTime, e.maxParticipants, e.createTime, e.critTime "
                + "FROM `event` e, `eventregstatus` ers, `eventstatus` es, `participant` p "
                + "WHERE e.eventregstatus_id = ers.id_eventregstatus "
                + "AND e.eventstatus_id = es.id_eventstatus "
                + "AND e.id_event = p.event_id "
                + "AND p.isAuthor = 1 ";

        // логика формирования итоговой строки запроса
        if (dateFrom != null & dateTo != null) {
            sql = sql + "AND e.eventTime >= ? AND e.eventTime <= ? ";
        }

        if (id_author != 0) {
            //sql
            sql = sql + "AND p.user_id = ? ";
        }

        if (!searchDesc.isEmpty()) {
            //sql
            sql = sql + "AND (MATCH (e.name, e.description, e.place) AGAINST (?) "
                    + "OR e.name LIKE ? "
                    + "OR e.description LIKE ? "
                    + "OR e.place LIKE ?)";
        }

        PreparedStatement pstm = con.prepareStatement(sql);

        //формирование параметров запроса
        int par = 1;
        if (dateFrom != null & dateTo != null) {
            pstm.setLong(par, dateFrom.toEpochSecond(ZoneOffset.UTC));
            par++;
            pstm.setLong(par, dateTo.toEpochSecond(ZoneOffset.UTC));
            par++;
        }

        if (id_author != 0) {
            pstm.setInt(par, id_author);
            par++;
        }

        
        
        if (!searchDesc.isEmpty()) {
            
            searchDesc = searchDesc.replace("*", "%");
            
            pstm.setString(par, searchDesc);
            par++;
            pstm.setString(par, searchDesc);
            par++;
            pstm.setString(par, searchDesc);
            par++;
            pstm.setString(par, searchDesc);
        }

        if (pstm.getParameterMetaData().getParameterCount() == 0) {
            throw new SearchException("Поиск не выполнен. Не заполнен хотя бы один параметр (обе даты, автор, текст)");
        }

        ResultSet rs = pstm.executeQuery();
        List<Event> list = new ArrayList<>();

        while (rs.next()) {

            Event ev = new Event(rs);
            List<Participant> participantList = EventDbQuery.selectParticipantsOfEvent(con, ev);
            ev.setParticipants(participantList);
            list.add(ev);
        }
        return list;
    }

    public static void deleteParticipantsOfEvent(Connection con, Event ev) throws SQLException {
        String sql = "DELETE FROM participant WHERE event_id=?";
        
        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, ev.getId_event());

        ptsm.executeUpdate();
    }
    
    public static void deleteEvent(Connection con, Event ev) throws SQLException {
        String sql = "DELETE FROM event WHERE id_event=?";
        
        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setInt(1, ev.getId_event());

        ptsm.executeUpdate();
    }
}