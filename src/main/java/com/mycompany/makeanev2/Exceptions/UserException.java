package com.mycompany.makeanev2.Exceptions;

public class UserException extends Exception {

    private final String ExcText; //текст исключения для передачи на страницу, тексты генерируются в check-методах User

    public UserException(String ExcText) {
        this.ExcText = ExcText;
    }

    @Override
    public String toString() {
        return ExcText;
    }
}
