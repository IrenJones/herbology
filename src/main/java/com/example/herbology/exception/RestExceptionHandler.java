package com.example.herbology.exception;

import com.example.herbology.model.ErrorModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // here we can define methods to handle specific exceptions
    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<ErrorModel> handleEntityNotFound(EntityNotFoundException ex){
        ErrorModel error = new ErrorModel(
                HttpStatus.NOT_FOUND,
                LocalDateTime.now(),
                "Entity not found",
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityWthErrorsException.class)
    private ResponseEntity<ErrorModel> handleEntityNotFound(EntityWthErrorsException ex){
        ErrorModel error = new ErrorModel(
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "Entity contains some errors",
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorModel error = new ErrorModel(
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                "Validation Error",
                ex.getBindingResult().toString()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
