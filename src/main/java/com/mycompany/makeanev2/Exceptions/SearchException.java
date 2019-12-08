/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.makeanev2.Exceptions;


public class SearchException extends Exception {
    
        //текст исключения для передачи на страницу
    private final String ExcText;

    //конструктор исключения с передачей сообщения
    public SearchException(String ExcText) {
        this.ExcText = ExcText;
    }

    //переопределили метод вывода объекта в строке, теперь он возвращает информацию о проблеме
    @Override
    public String toString() {
        return ExcText;
    }
    
    
}
