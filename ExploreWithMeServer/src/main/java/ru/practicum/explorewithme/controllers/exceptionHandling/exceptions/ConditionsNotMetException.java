package ru.practicum.explorewithme.controllers.exceptionHandling.exceptions;

public class ConditionsNotMetException extends RuntimeException {

    public ConditionsNotMetException(String message) {
        super(message);
    }
}
