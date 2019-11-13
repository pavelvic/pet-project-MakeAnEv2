package com.mycompany.makeanev2;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class Participant {
    
    private final Event event;
    private final User person;
    private final ParticipantStatus status;
    private final User whoAdd;
    private boolean isAuthor;
    private final ZonedDateTime regDatetime;
    private ZoneId zone;
    
    public Participant(Event event, User person, ParticipantStatus status, User whoAdd, ZonedDateTime regDatetime) {
        this.event = event;
        this.person = person;
        this.status = status; //основной или запасной участник
        this.whoAdd = whoAdd;
        this.isAuthor = false;
        this.regDatetime = regDatetime; //дата добавления в участники
        this.zone = ZoneId.of("UTC");
    }
    
    public Participant(Event event, User person, ParticipantStatus status, User whoAdd, int isAuthor, ZonedDateTime regDatetime) {
        this.event = event;
        this.person = person;
        this.status = status; //основной или запасной участник
        this.whoAdd = whoAdd;
        this.isAuthor = isAuthor != 0;
//        this.isAuthor = false;
        this.regDatetime = regDatetime; //дата добавления в участники
        this.zone = ZoneId.of("UTC");
    }

    public void setZone(ZoneId zone) {
        this.zone = zone;
    }
    
    public void setAsAuthor() {
        this.isAuthor = true;
    }

    public Event getEvent() {
        return event;
    }

    public User getPerson() {
        return person;
    }

    public ParticipantStatus getStatus() {
        return status;
    }

    public User getWhoAdd() {
        return whoAdd;
    }

    public boolean getAuthor() {
        return isAuthor;
    }

    public String getRegDatetime() {
        return regDatetime.withZoneSameInstant(zone).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }
    
    public ZonedDateTime getZonedRegDatetime() {
        return regDatetime;
    }
    
    
    
    
    
}