package com.citronix.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

  @RestControllerAdvice
    public class GlobalExceptionHandler {


        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND.value(),
                    "RESOURCE_NOT_FOUND",
                    "The resource you are looking for does not exist.",
                    request.getDescription(false)
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    "An unexpected error occurred.",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "INTERNAL_SERVER_ERROR",
                    ex.getMessage(),
                    request.getDescription(false)
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
            ErrorResponse errorResponse = new ErrorResponse(
                    LocalDateTime.now(),
                    ex.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    "BAD_REQUEST",
                    "The request contains invalid arguments.",
                    request.getDescription(false)
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }


    }
