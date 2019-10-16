package com.mycompany.makeanev2.Utils;

import com.mycompany.makeanev2.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.ZoneOffset;


public class EventDbQuery {
  public static void insertEvent(Connection con, Event ev) throws SQLException {
        
        //в подходах java определено использовать символ "?" как параметр sql запроса
        String sql = "INSERT INTO event(eventstatus_id, eventregstatus_id, name, description, place, eventTime, maxParticipants, createTime, critTime) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);

        //здесь определяем параметры (символ "?") для sql-запроса на основе объекта
        ps.setInt(1, ev.getEventstatus_id());
        ps.setInt(2, ev.getEventregstatus_id());
        ps.setString(3, ev.getName());
        ps.setString(4, ev.getDescription());
        ps.setString(5, ev.getPlace());
        

        ps.setLong(6, ev.getEventTime().toEpochSecond(ZoneOffset.UTC));
        
        ps.setInt(7, ev.getMaxParticipants());
        
        //аналогично для другого поля с датой
        ps.setLong(8,ev.getCreateTime().toEpochSecond());
        ps.setLong(9,ev.getCritTime().toEpochSecond(ZoneOffset.UTC));

        //ВЫПОЛНЯЕМ sql-запрос
        ps.executeUpdate();
    }  
}