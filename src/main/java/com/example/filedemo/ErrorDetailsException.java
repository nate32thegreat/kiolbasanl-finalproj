package com.example.filedemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class ErrorDetailsException extends ResponseEntityExceptionHandler {

    @ResponseStatus(value=HttpStatus.NOT_FOUND,reason= "Make sure the key has the name 'file' and that a file is chosen")
    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> inputExceptions(NullPointerException ex, WebRequest request)    {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
    }

//    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason= "Make sure the key has the name 'file' and that a file is chosen")
//    @ExceptionHandler(NullPointerException.class)
//    public void conflict() {
//
//    }

}
