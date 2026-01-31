package org.example.libreraymanagementsystem_byprudwiceo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobExcep {

    @ExceptionHandler(BookException.class)
    public ResponseEntity<?>bookException(BookException b)
    {
        return new ResponseEntity<>(b.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AppointmentException.class)
    public ResponseEntity<?>AppointmentException(AppointmentException e)
    {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
}
