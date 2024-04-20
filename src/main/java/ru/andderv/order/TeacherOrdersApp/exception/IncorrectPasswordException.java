package ru.andderv.order.TeacherOrdersApp.exception;

/**
 * @author andderV
 * @date 05.04.2024 7:06
 * TeacherOrdersApp
 */
public class IncorrectPasswordException extends Throwable{

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
