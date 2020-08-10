package com.mycompany.makeanev2;

import java.sql.ResultSet;
import java.sql.SQLException;

/*класс для работы с сущностью группа пользователей, соотв. полям в БД*/
//в проекте используется для формирования списка групп на странице с редактированием пользователя
public class UserGroup {

    private final int id_group; //уникальный номер
    private final String name; //имя

    //стандартный конструктор по умолчанию
    public UserGroup(int id_group, String name) {
        this.id_group = id_group;
        this.name = name;
    }

    //конструктор на основе sql-запроса
    public UserGroup(ResultSet rs) throws SQLException {
        if (rs == null) {
            throw new IllegalArgumentException("Недопустимый аргумент (null)");
        }
        this.id_group = rs.getInt(1);
        this.name = rs.getString(2);
    }

    //геттеры сеттеры
    public int getId_group() {
        return id_group;
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

        UserGroup us = (UserGroup) obj;
        return id_group == us.id_group && name.equals(us.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id_group + this.name.hashCode() * 13;
        return hash;
    }
}
