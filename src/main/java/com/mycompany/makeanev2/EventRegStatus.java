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
}
