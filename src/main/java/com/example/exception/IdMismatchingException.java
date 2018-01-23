package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IdMismatchingException extends Exception {
    public IdMismatchingException(String message) {
        super(message);
    }
}