package com.mycompany.makeanev2;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventRegStatus {

    private final int id_eventRegStatus;
    private final String name;

    public EventRegStatus(int id_eventRegStatus, String name) {
        this.id_eventRegStatus = id_eventRegStatus;
        this.name = name;
    }

    public EventRegStatus(ResultSet rs) throws SQLException {
        this.id_eventRegStatus = rs.getInt(1);
        this.name = rs.getString(2);
    }

    public int getId_eventRegStatus() {
        return id_eventRegStatus;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        EventRegStatus pobj = (EventRegStatus) obj;
        return id_eventRegStatus == pobj.id_eventRegStatus && name.equals(pobj.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id_eventRegStatus + this.name.hashCode() * 3;
        return hash;
    }
}
