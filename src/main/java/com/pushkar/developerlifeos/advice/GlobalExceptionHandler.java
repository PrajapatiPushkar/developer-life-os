package com.pushkar.developerlifeos.advice;

import com.pushkar.developerlifeos.exception.TaskNotFoundException;
import com.pushkar.developerlifeos.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pushkar.developerlifeos.dto.ErrorResponse;
import com.pushkar.developerlifeos.exception.InvalidCredentialsException;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleTaskNotFound(
            TaskNotFoundException ex){

        log.error("Exception occurred: {}", ex.getMessage());

        return new ResponseEntity<>(
                ex.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>>
    handleValidationExceptions(
            MethodArgumentNotValidException ex){

        log.error("Exception occurred: {}", ex.getMessage());

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    errors.put(
                            error.getField(),
                            error.getDefaultMessage());

                });

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex){

        log.error("Exception occurred: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(

                LocalDateTime.now(),

                404,

                "Not Found",

                ex.getMessage()

        );

        return ResponseEntity.status(404).body(error);

    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException ex){
        log.error("Exception occurred: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(

                LocalDateTime.now(),

                401,

                "Unauthorized",

                ex.getMessage()

        );

        return ResponseEntity.status(401).body(error);

    }

}