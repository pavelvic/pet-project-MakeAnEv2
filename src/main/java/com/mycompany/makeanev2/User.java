package com.mycompany.makeanev2;

public class User {

    /*класс для работы с объектом пользователь, формируется из данных в БД*/
    private final int id_user;
    private final String username;
    private final int password;
    private final String passwordStr;
    private final String email;
    private final String phone;
    private final String name;
    private final String surname;
    private final String comment;

    //TODO: подумать над оптимизацией конструкторов - в частности сделать в конструкторе параметр - httpservletreques и разбирать его там
    public User(int id_user, String username, String password, String email, String phone, String name, String surname, String comment) {
        this.id_user = id_user;
        this.username = username;
        this.password = password.hashCode();
        this.passwordStr = password;
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
        this.passwordStr = ""; //???
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.comment = comment;
    }

    //геттеры сеттеры
    public int getId_user() {
        return id_user;
    }

    public String getPasswordStr() {
        return passwordStr;
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

    //проверка значений
    public void checkUsername() throws UserException {
        String usernamePattern = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
        if (!username.matches(usernamePattern)) {
            throw new UserException("Имя пользователя не заполнено или не соответсвует требованиям: от 2 до 20 символов из латинских букв и цифр, первый символ обязательно буква");
        }
    }

    public void checkEmail() throws UserException {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(emailPattern)) {
            throw new UserException("E-mail не заполнен или не соответсвует формату ИмяПользователя@домен.*");
        }

    }

    public void checkPassword() throws UserException {
        String PassPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (!passwordStr.matches(PassPattern)) {
            throw new UserException("Пароль не удовлетовряет условиям: не менее 8 символов, cодержит хотя бы одну цифру, "
                    + "содерижт хотя бы одну букву в верхнеи и нижнем регистре, "
                    + "содержит хотя бы один спеццсимвол(@#%$^ и т.д.), "
                    + "не содержит пробелов");
        }
    }

    public void checkPhone() throws UserException {
        String phonePattern = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        if ((!phone.matches(phonePattern)) && (!"".equals(phone))) {
            throw new UserException("Телефон введён некорректно(формат +7ХХХХХХХХХХ)");
        }
    }
}
