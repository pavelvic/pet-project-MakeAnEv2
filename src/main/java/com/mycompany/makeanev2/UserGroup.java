
package com.mycompany.makeanev2;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserGroup {
    private final int id_group;
    private final String name;

    public UserGroup(int id_group, String name) {
        this.id_group = id_group;
        this.name = name;
    }
    
    public UserGroup(ResultSet rs) throws SQLException {
        this.id_group = rs.getInt(1);
        this.name = rs.getString(2);
    }

    public int getId_group() {
        return id_group;
    }

    public String getName() {
        return name;
    }
 }
