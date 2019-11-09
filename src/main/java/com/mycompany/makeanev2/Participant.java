package com.mycompany.makeanev2;

import java.time.ZonedDateTime;


public class Participant {
    
    private final Event event;
    private final User person;
    private final ParticipantStatus status;
    private final User whoAdd;
    private boolean isAuthor;
    private final ZonedDateTime regDatetime;
    
    public Participant(Event event, User person, ParticipantStatus status, User whoAdd, ZonedDateTime regDatetime) {
        this.event = event;
        this.person = person;
        this.status = status; //основной или запасной участник
        this.whoAdd = whoAdd;
        this.isAuthor = false;
        this.regDatetime = regDatetime; //дата добавления в участники
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

    public boolean isIsAuthor() {
        return isAuthor;
    }

    public ZonedDateTime getRegDatetime() {
        return regDatetime;
    }
    
    
    
    
    
}