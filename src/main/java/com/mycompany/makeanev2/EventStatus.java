package com.mycompany.makeanev2;

import java.sql.SQLException;


public class EventStatus {
    private final int id_eventStatus;
    private final String name;
    private String checked;

    public EventStatus(int id_eventStatus, String name) {
        this.id_eventStatus = id_eventStatus;
        this.name = name;
        this.checked = "";
    }

    //конструктор по умолчанию
    public EventStatus() throws SQLException {
        this.id_eventStatus = 1;
        this.name = "Планируется";
        this.checked = "";
    }

    public int getId_eventStatus() {
        return id_eventStatus;
    }

    public String getName() {
        return name;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked() {
        this.checked = "checked";
    }
    
    public void setNotChecked() {
        this.checked = "";
    }
}
