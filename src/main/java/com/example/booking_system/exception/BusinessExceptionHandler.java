package com.example.booking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BusinessExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException exception) {
        var response = new ApiErrorResponse()
                .setErrorCode(exception.getErrorCode())
                .setStatusCode(460);
        if (exception.getDetails() != null && !exception.getDetails().isEmpty())
            response.setDetails(exception.getDetails());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
