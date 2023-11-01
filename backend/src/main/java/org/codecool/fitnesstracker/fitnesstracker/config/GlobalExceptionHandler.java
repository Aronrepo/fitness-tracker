package org.codecool.fitnesstracker.fitnesstracker.config;

import org.codecool.fitnesstracker.fitnesstracker.exceptions.*;
import org.codecool.fitnesstracker.fitnesstracker.user.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleRoomNotFoundException(EmailNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(NoSuchFoodTypeException.class)
    public ResponseEntity<String> handleNoSuchFoodTypeException(NoSuchFoodTypeException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
    }

    @ExceptionHandler(ZeroInputException.class)
    public ResponseEntity<String> handleZeroInputException(ZeroInputException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<String> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
