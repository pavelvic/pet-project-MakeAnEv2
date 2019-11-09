package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Exceptions.EventException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Event {

    //переменные
    private final int id_event; //уник идентификатор события, автоинкретемнт в БД
    private final EventStatus evStatus; //код статуса мероприятия, из таблицы статусов
    //private final int eventregstatus_id; //код статуса регистрации на мероприятие из таблицы статусов
    private final EventRegStatus evRegStatus;
    private final String name; //название события, обязательное
    private final String description; //описание события, не обязательное
    private final String place; //место проведения, обязательное
    private final ZonedDateTime eventTime; //время проведения, указывается всегда в местном времени
    private final int maxParticipants; //макс число участников
    private final ZonedDateTime createTime; //Дата создания
    private final ZonedDateTime critTime; //критичное время отказа от участия ???
    private  ZoneId zone;

    //общий конструктор
    public Event(int id_event, EventStatus evStatus, EventRegStatus evRegStatus, String name, String description, String place, ZonedDateTime eventTime, int maxParticipants, ZonedDateTime createTime, ZonedDateTime critTime) {
        this.id_event = id_event;
        this.evStatus = evStatus;
        this.evRegStatus = evRegStatus;
        this.name = name;
        this.description = description;
        this.place = place;
        this.eventTime = eventTime;
        this.maxParticipants = maxParticipants;
        this.createTime = createTime;
        this.critTime = critTime;
        this.zone = ZoneId.of("UTC");
    }

    //конструктор по ResultSet для работы с БД
    //TODO: отладить после создания реализации метода запроса БД
    public Event(ResultSet rs) throws SQLException {
        this.id_event = rs.getInt(1);
        this.evStatus = new EventStatus(rs.getInt(2), rs.getString(3));
        this.evRegStatus = new EventRegStatus(rs.getInt(4), rs.getString(5));
        this.name = rs.getString(6);
        this.description = rs.getString(7);
        this.place = rs.getString(8);
        //this.eventTime = LocalDateTime.ofEpochSecond(rs.getLong(9), 0, ZoneOffset.UTC);
        this.zone = ZoneId.of("UTC");
        this.eventTime = ZonedDateTime.of(LocalDateTime.ofEpochSecond(rs.getLong(9), 0, ZoneOffset.UTC), zone);
        this.maxParticipants = rs.getInt(10);
        this.createTime = ZonedDateTime.of(LocalDateTime.ofEpochSecond(rs.getLong(11), 0, ZoneOffset.UTC), zone);
        this.critTime = ZonedDateTime.of(LocalDateTime.ofEpochSecond(rs.getLong(12), 0, ZoneOffset.UTC), zone);
    }

    //геттеры сеттеры
    public int getId_event() {
        return id_event;
    }

    public EventStatus getEvStatus() {
        return evStatus;
    }

    public EventRegStatus getEvRegStatus() {
        return evRegStatus;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPlace() {
        return place;
    }

    public String getEventTime() {
        return eventTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
    
    public ZonedDateTime getZonedEventTime() {
        return eventTime;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public String getCreateTime() {
        return createTime.withZoneSameInstant(zone).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
    
    public ZonedDateTime getZonedCreateTime() {
        return createTime;
    }

    
    public String getCritTime() {
        return critTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
    
    public ZonedDateTime getZonedCritTime() {
        return critTime;
    }
    
    
    public void setZone(ZoneId zone) {
        this.zone = zone;
    }

    //другие методы: проверки значений, изменение, equals() и hashCode()
    //проверка: критичная дата время должна быть меньше даты времени события
        public void checkTimes() throws EventException {
        
//        if (eventTime.isBefore(LocalDateTime.now(Clock.systemUTC()))) {
//            throw new EventException("Дата события не может быть в прошлом");
//        }

        if (eventTime.isBefore(ZonedDateTime.now(ZoneId.of("UTC")))) {
            throw new EventException("Дата события не может быть в прошлом");
        }
        
        if (!critTime.isBefore(eventTime)) {
            throw new EventException("Критическая дата должна быть раньше Даты события");
        }
        
        if (critTime.isBefore(ZonedDateTime.now(ZoneId.of("UTC")))) {
            throw new EventException("Критическая дата не может быть в прошлом");
        }
    }
    
    
}
