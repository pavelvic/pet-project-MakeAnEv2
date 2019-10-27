package com.mycompany.makeanev2;

import java.sql.SQLException;


public class EventStatus {
    private final int id_eventStatus;
    private final String name;

    public EventStatus(int id_eventStatus, String name) {
        this.id_eventStatus = id_eventStatus;
        this.name = name;
    }

    //конструктор по умолчанию
    public EventStatus() throws SQLException {
        this.id_eventStatus = 1;
        this.name = "Планируется";
    }

    public int getId_eventStatus() {
        return id_eventStatus;
    }

    public String getName() {
        return name;
    }
}
