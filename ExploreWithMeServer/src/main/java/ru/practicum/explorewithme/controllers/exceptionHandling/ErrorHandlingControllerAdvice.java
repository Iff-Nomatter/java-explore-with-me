package ru.practicum.explorewithme.controllers.exceptionHandling;

import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.ConditionsNotMetException;
import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.EntryNotFoundException;
import ru.practicum.explorewithme.controllers.exceptionHandling.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.explorewithme.model.ApiError;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e
    ) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
        violationLogging(violations);
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        violationLogging(violations);
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError onValidationException(ValidationException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
    }

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError onEntryNotFoundException(final EntryNotFoundException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        ));
    }

    @ResponseBody
    @ExceptionHandler(ConditionsNotMetException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError onConditionsNotMetException(ConditionsNotMetException exception) {
        log.error(exception.getMessage());
        return new ApiError(
                exception.getMessage(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                HttpStatus.FORBIDDEN.toString(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                ));
    }

    //логгирование нарушений валидации
    public void violationLogging(List<Violation> violations) {
        for (Violation violation : violations) {
            log.error(violation.getFieldName() + ": " + violation.getMessage());
        }
    }

}
