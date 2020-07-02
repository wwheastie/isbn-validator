package com.acme.isbnvalidator.exception;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.StringJoiner;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleConstraintViolationException(MethodArgumentNotValidException mae) {
        StringJoiner joiner = new StringJoiner(", ");

        //Iterate through failed validated inputs and create message for fail reason
        for(FieldError error : mae.getBindingResult().getFieldErrors()) {
            //Get property name
            String name = error.getField();

            //Get property error message
            String message = error.getDefaultMessage();

            joiner.add(name + ": " + message);
        }

        //Log error
        logger.error("Request validation failed: " + joiner.toString());

        //Create new error details
        ErrorDetails errorDetails = new ErrorDetails(joiner.toString(), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException cve) {
        StringJoiner joiner = new StringJoiner(", ");

        //Iterate through failed validated inputs and create message for fail reason
        for(ConstraintViolation<?> constraintViolation: cve.getConstraintViolations()) {
            //Get property name
            PathImpl path = (PathImpl)constraintViolation.getPropertyPath();
            String name = path.getLeafNode().getName();

            //Get property error message
            String message = constraintViolation.getMessage();

            joiner.add(name + ": " + message);
        }

        //Log error
        logger.error("Request validation failed: " + joiner.toString());

        //Create new error details
        ErrorDetails errorDetails = new ErrorDetails(joiner.toString(), HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        //Log error
        logger.error("Exception: ", e);

        //Create new error details
        ErrorDetails errorDetails = new ErrorDetails("Internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
