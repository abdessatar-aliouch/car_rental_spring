package com.aa.carrental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

/**
 * @author Abdessatar Aliouch
 * @created 26/12/2022
 */

public class ConflictException extends RuntimeException {
    public ConflictException(String s) {
        super(s);
    }
}
