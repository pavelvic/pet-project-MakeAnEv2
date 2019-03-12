package com.mycompany.makeanev2;


public class User {
    /*класс для работы с объектом пользователь, формируется из данных в БД*/
    
    private final int id_user; 
    private final String username;
    private final int password;
    private final String email;
    private final String phone;
    private final String name;
    private final String surname;
    private final String comment;

    
    //TODO: подумать над оптимизацией конструкторов???
    public User(int id_user, String username, String password, String email, String phone, String name, String surname, String comment) {
        this.id_user = id_user;
        this.username = username;
        this.password = password.hashCode();
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.comment = comment;
    }
    public User(int id_user, String username, String email, String phone, String name, String surname, String comment) {
        this.id_user = id_user;
        this.username = username;
        this.password = 0;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.comment = comment;
    }
    

    

    public int getId_user() {
        return id_user;
    }

    
    public String getUsername() {
        return username;
    }

    public int getPassword() {
        return password;
    }
    
    
    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getComment() {
        return comment;
    }
    
 }
