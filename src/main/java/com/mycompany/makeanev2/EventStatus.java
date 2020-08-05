package com.mycompany.makeanev2;

import java.sql.SQLException;

public class EventStatus {

    private final int id_eventStatus;
    private final String name;
    private String checked;

    public EventStatus(int id_eventStatus, String name) {
        if (id_eventStatus > 3 || id_eventStatus < 1) {
            throw new IllegalArgumentException("Недопустимый id статуса (должен быть 1,2,3)");
        }
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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        EventStatus pobj = (EventStatus) obj;
        return id_eventStatus == pobj.id_eventStatus && name.equals(pobj.name) && checked.equals(pobj.checked);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id_eventStatus + this.name.hashCode() * 13 + checked.hashCode() * 15;
        return hash;
    }
}
