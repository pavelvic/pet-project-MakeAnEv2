package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Exceptions.EventException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Event {

    //переменные
    private final int id_event; //уник идентификатор события, автоинкретемнт в БД
    private final EventStatus evStatus; //код статуса мероприятия, из таблицы статусов
    //private final int eventregstatus_id; //код статуса регистрации на мероприятие из таблицы статусов
    private final EventRegStatus evRegStatus;
    private final String name; //название события, обязательное
    private final String description; //описание события, не обязательное
    private final String place; //место проведения, обязательное
    private final LocalDateTime eventTime; //время проведения, указывается всегда в местном времени
    private final int maxParticipants; //макс число участников
    private final ZonedDateTime createTime; //Дата создания
    private final ZonedDateTime critTime; //критичное время отказа от участия ???

    //общий конструктор
    public Event(int id_event, EventStatus evStatus, EventRegStatus evRegStatus, String name, String description, String place, LocalDateTime eventTime, int maxParticipants, ZonedDateTime createTime, ZonedDateTime critTime) {
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
    }

    
    
    //конструктор по ResultSet для работы с БД
    //TODO: отладить после создания реализации метода запроса БД
//    public Event(ResultSet rs) {
//        this.id_event = id_event;
//        this.eventstatus_id = eventstatus_id;
//        this.name = name;
//        this.description = description;
//        this.place = place;
//        this.eventTime = eventTime;
//        this.MaxParticipants = MaxParticipants;
//        this.createTime = createTime;
//        this.critTime = critTime;
//    }

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

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    
    public LocalDateTime getCritTime() {
        return critTime.toLocalDateTime();
    }


    //другие методы: проверки значений, изменение, equals() и hashCode()
    //проверка: критичная дата время должна быть меньше даты времени события
        public void checkTimes() throws EventException {
        
        if (eventTime.isBefore(LocalDateTime.now(Clock.systemUTC()))) {
            throw new EventException("Дата события не может быть в прошлом");
        }
        
        if (!critTime.toLocalDateTime().isBefore(eventTime)) {
            throw new EventException("Критическая дата должна быть раньше Даты события");
        }
        
        if (critTime.toLocalDateTime().isBefore(LocalDateTime.now(Clock.systemUTC()))) {
            throw new EventException("Критическая дата не может быть в прошлом");
        }
    }
    
    
}
