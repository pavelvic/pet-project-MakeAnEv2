package com.mycompany.makeanev2.Exceptions;

public class EventException extends Exception {
    //текст исключения для передачи на страницу

    private final String ExcText;

    //конструктор исключения с передачей сообщения
    public EventException(String ExcText) {
        this.ExcText = ExcText;
    }

    //переопределили метод вывода объекта в строке, теперь он возвращает информацию о проблеме
    @Override
    public String toString() {
        return ExcText;
    }
}
