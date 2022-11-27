package com.voting.votingsystem;

import com.voting.votingsystem.Entities.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CustomExceptionHandler {
    @ControllerAdvice
    @ResponseStatus
    public static class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
        @ExceptionHandler(Exceptions.class)
        public ResponseEntity<ErrorMessage> departmentNotFoundException(
                Exceptions exception, WebRequest request) {
            ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST,
                    exception.getMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(message);
        }
    }
}
