package com.mycompany.makeanev2;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class Participant {

    private final int id_participant;
    private final User person;
    private final ParticipantStatus status;
    private final User whoAdd;
    private boolean isAuthor;
    private final ZonedDateTime regDatetime;
    private ZoneId zone;

    public Participant(User person, ParticipantStatus status, User whoAdd, ZonedDateTime regDatetime) {
        this.id_participant = 0;
        this.person = person;
        this.status = status; //основной или запасной участник
        this.whoAdd = whoAdd;
        this.isAuthor = false;
        this.regDatetime = regDatetime; //дата добавления в участники
        this.zone = ZoneId.of("UTC");
    }

    public Participant(int id_participant, User person, ParticipantStatus status, User whoAdd, int isAuthor, ZonedDateTime regDatetime) {
        if (id_participant < 0) {
            throw new IllegalArgumentException("Недопустимое значение id (<0)");
        }
        if (isAuthor != 1) {
            if (isAuthor != 0) {
                throw new IllegalArgumentException("Недопустимое значение isAuthor (должно быть 1 или 0)");
            }
        }
        this.id_participant = id_participant;
        this.person = person;
        this.status = status; //основной или запасной участник
        this.whoAdd = whoAdd;
        this.isAuthor = isAuthor != 0;
        this.regDatetime = regDatetime; //дата добавления в участники
        this.zone = ZoneId.of("UTC");
    }

    public void setZone(ZoneId zone) {
        this.zone = zone;
    }

    public void setAsAuthor() {
        this.isAuthor = true;
    }

    public ZoneId getZone() {
        return zone;
    }

    public int getId_participant() {
        return id_participant;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id_participant;
        hash = 53 * hash + Objects.hashCode(this.person);
        hash = 53 * hash + Objects.hashCode(this.status);
        hash = 53 * hash + Objects.hashCode(this.whoAdd);
        hash = 53 * hash + (this.isAuthor ? 1 : 0);
        hash = 53 * hash + Objects.hashCode(this.regDatetime);
        hash = 53 * hash + Objects.hashCode(this.zone);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Participant other = (Participant) obj;
        if (this.id_participant != other.id_participant) {
            return false;
        }
        if (this.isAuthor != other.isAuthor) {
            return false;
        }
        if (!Objects.equals(this.person, other.person)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.whoAdd, other.whoAdd)) {
            return false;
        }
        if (!Objects.equals(this.regDatetime, other.regDatetime)) {
            return false;
        }
        if (!Objects.equals(this.zone, other.zone)) {
            return false;
        }
        return true;
    }

}
