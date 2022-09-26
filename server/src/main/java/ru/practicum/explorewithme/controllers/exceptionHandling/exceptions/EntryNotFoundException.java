package ru.practicum.explorewithme.controllers.exceptionHandling.exceptions;

public class EntryNotFoundException extends RuntimeException {

    public EntryNotFoundException(String message) {
        super(message);
    }
}
