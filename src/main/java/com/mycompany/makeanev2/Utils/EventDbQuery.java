package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Event;
import com.mycompany.makeanev2.EventRegStatus;
import com.mycompany.makeanev2.Participant;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;
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
        

        ps.setLong(7, ev.getEventTime().toEpochSecond(ZoneOffset.UTC));
        
        ps.setInt(8, ev.getMaxParticipants());
        
        //аналогично для другого поля с датой
        ps.setLong(9,ev.getCreateTime().toEpochSecond());
        ps.setLong(10,ev.getCritTime().toEpochSecond(ZoneOffset.UTC));

        //ВЫПОЛНЯЕМ sql-запрос
        ps.executeUpdate();
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
  
  public static EventRegStatus selectEventRegStatusById(Connection con, String id_eventRegStatusStr) throws SQLException {
        
        String sql = "SELECT id_eventregstatus, name "
                + "FROM `eventregstatus` "
                + "WHERE id_eventregstatus = ?";

        PreparedStatement ptsm = con.prepareStatement(sql);

        ptsm.setString(1, id_eventRegStatusStr);

        ResultSet rs = ptsm.executeQuery();

        if (rs.next()) {
            return new EventRegStatus(rs);
        }
        return null;
    }
  
  public static void insertParticipant(Connection con, Participant pct) throws SQLException {

          String sql = "INSERT INTO participant(event_id, user_id, participantStatus_id, user_id_whoadd, isAuthor, regDatetime) VALUES (?,?,?,?,?,?)";
          PreparedStatement ps = con.prepareStatement(sql);
          
          ps.setInt(1, pct.getEvent().getId_event());
          ps.setInt(2, pct.getPerson().getId_user());
          ps.setInt(3, pct.getStatus().getId_participantStatus());
          ps.setInt(4, pct.getWhoAdd().getId_user());
          ps.setBoolean(5, pct.isIsAuthor());
          ps.setLong(6, pct.getRegDatetime().toEpochSecond(ZoneOffset.UTC));
          
          //ВЫПОЛНЯЕМ sql-запрос
          ps.executeUpdate();
    }
}