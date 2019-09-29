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
}
