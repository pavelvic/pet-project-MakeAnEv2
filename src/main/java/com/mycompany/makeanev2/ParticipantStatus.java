package com.mycompany.makeanev2;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ParticipantStatus {
    private final int id_participantStatus;
    private final String name;

    public ParticipantStatus(int id_participantStatus, String name) {
        this.id_participantStatus = id_participantStatus;
        this.name = name;
    }

    public ParticipantStatus(ResultSet rs) throws SQLException {
        this.id_participantStatus = rs.getInt(1);
        this.name = rs.getString(2);
    }
    
    public ParticipantStatus()  {
        this.id_participantStatus = 1;
        this.name = "Основной";
    }

    public int getId_participantStatus() {
        return id_participantStatus;
    }

    public String getName() {
        return name;
    }
}
