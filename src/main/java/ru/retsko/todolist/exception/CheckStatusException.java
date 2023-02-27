package ru.retsko.todolist.exception;

public class CheckStatusException extends RuntimeException {
    public CheckStatusException(String message) {
        super(message);
    }
}
