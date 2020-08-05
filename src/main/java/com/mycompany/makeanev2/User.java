package com.mycompany.makeanev2;

import com.mycompany.makeanev2.Exceptions.UserException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/*контейнер для формирования пользователей и методы действия над ними*/
public class User {

    //параметры пользователя, соотв данным в полях БД
    private int id_user; //уникальный идентификатор
    private int group_id; //идентификатор группы пользователей, определяет права: 1 - суперпользователь, 2 - админ, 3 - менеджер, 4 - пользователь (рядовой), 5 - заблокированный
    private String groupname; //название группы пользователей
    private String username; //уникальное имя пользователя
    private int password; // hash пароль
    private String passwordStr; //парлоль в текстовом виде, не хранится в БД
    private String email; //почта
    private String phone; //телефон
    private String name; //имя 
    private String surname; //фамилия
    private String comment; //комментарий

    //конструктуор по параметрам (общий)
    public User(int id_user, int group_id, String groupname, String username, int hashpassword, String password, String email, String phone, String name, String surname, String comment) {
        this.id_user = id_user;
        this.group_id = group_id;
        this.groupname = groupname;
        this.username = username;
        this.password = hashpassword;
        this.passwordStr = password;
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.surname = surname;
        this.comment = comment;
    }

    //конструктор для формирования пользователя по результату SQL-запроса (на вход подаем результат sql запроса в виде ResultSet)
    public User(ResultSet rs) throws SQLException {
        this.id_user = rs.getInt(1);
        this.group_id = rs.getInt(2);
        this.groupname = rs.getString(3);
        this.username = rs.getString(4);
        this.password = rs.getInt(5);
        this.passwordStr = "";
        this.email = rs.getString(6);
        this.phone = rs.getString(7);
        this.name = rs.getString(8);
        this.surname = rs.getString(9);
        this.comment = rs.getString(10);
    }

    //геттеры сеттеры в соответствии с параметрами (описаны при объявлении в начале класса)
    public int getId_user() {
        return id_user;
    }

    public int getGroup_id() {
        return group_id;
    }

    public String getGroupname() {
        return groupname;
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

    public void setPassword(String password) {
        this.password = password.hashCode();
        this.passwordStr = password;
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

    //механизм "пакетного" внесения изменений по коллекции данных (позволяет указывать произвольное количество полей для изменений)
    public void applyChanges(Map updateMap) {

        if (updateMap == null) {
            throw new IllegalArgumentException("Недопустимый аргумент: пустой объект (null)");
        }

        //проверяем в коллекции наличие параметра, если находим - меняем в объекте, к которому данный метод применяется
        if (updateMap.get("id_user") != null) {
            id_user = Integer.parseInt((String) updateMap.get("id_user"));
        }

        if (updateMap.get("group_id") != null) {
            group_id = Integer.parseInt((String) updateMap.get("group_id"));
        }

        if (updateMap.get("groupname") != null) {
            groupname = (String) updateMap.get("groupname");
        }

        if (updateMap.get("username") != null) {
            username = (String) updateMap.get("username");
        }

        if (updateMap.get("password") != null) {
            password = Integer.parseInt((String) updateMap.get("password"));
        }

        if (updateMap.get("passwordStr") != null) {
            passwordStr = (String) updateMap.get("passwordStr");
        }

        if (updateMap.get("email") != null) {
            email = (String) updateMap.get("email");
        }

        if (updateMap.get("phone") != null) {
            phone = (String) updateMap.get("phone");
        }

        if (updateMap.get("name") != null) {
            name = (String) updateMap.get("name");
        }

        if (updateMap.get("surname") != null) {
            surname = (String) updateMap.get("surname");
        }

        if (updateMap.get("comment") != null) {
            comment = (String) updateMap.get("comment");
        }
    }

    //проверка имени пользователя на наличие недопустимых символов через реглуярные выражения, при непрохождении выдаем исключение, которое обрабатываем и выводим как сообщение ползователю
    public void checkUsernamePattern() throws UserException {
        String usernamePattern = "^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$";
        if (!username.matches(usernamePattern)) {
            throw new UserException("Имя пользователя не заполнено или не соответсвует требованиям: от 2 до 20 символов из латинских букв и цифр, первый символ обязательно буква");
        }
    }

    //проверка e-mail На соответствие формату
    public void checkEmailPattern() throws UserException {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (!email.matches(emailPattern)) {
            throw new UserException("E-mail не заполнен или не соответсвует формату ИмяПользователя@домен.*");
        }
    }

    //проверка требований к безопасности пароля
    public void checkPasswordPattern() throws UserException {
        String passPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        if (!passwordStr.matches(passPattern)) {
            throw new UserException("Пароль не удовлетовряет условиям: не менее 8 символов, cодержит хотя бы одну цифру, "
                    + "содерижт хотя бы одну букву в верхнем и нижнем регистре, "
                    + "содержит хотя бы один спецсимвол(@#%$^ и т.д.), "
                    + "не содержит пробелов");
        }
    }

    //проверка формата телефона
    public void checkPhonePattern() throws UserException {
        String phonePattern = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
        if ((!phone.matches(phonePattern)) && (!"".equals(phone))) {
            throw new UserException("Телефон введён некорректно(формат +7ХХХХХХХХХХ)");
        }
    }

    //метод проверки на уникальность пользователя (с возможностью исключения из проверки перечней пользователей)
    public void checkUniqueUser(List<User> allUsers, List<User> remUsers) throws UserException {
        if (allUsers == null || remUsers == null) {
            throw new IllegalArgumentException("Недопустимые аргументы (null)");
        }
        allUsers.removeAll(remUsers);

        for (User oneUser : allUsers) {

            if (username.equals(oneUser.getUsername())) {
                throw new UserException("Недопустимое значение Имя пользователя. Такой пользователь есть в системе"); //уникальность username
            }
            if (email.equals(oneUser.getEmail())) {
                throw new UserException("Недопустимое значение E-mail. Пользователь с таким e-mail есть в системе"); //уникальность email
            }
            if (phone.equals(oneUser.getPhone())) {
                throw new UserException("Недопустимое значение Телефона. Пользователь с таким телефоном есть в системе"); //уникальность phone
            }
        }
    }

    //переопределяем методы сравнения объектов для использования в колленкциях и других местах, задаем правила по которым объекты сравниваются
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        User us = (User) obj;
        return id_user == us.id_user;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id_user;
        return hash;
    }
}
