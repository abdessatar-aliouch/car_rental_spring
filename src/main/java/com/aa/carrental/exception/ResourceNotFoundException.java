package com.aa.carrental.exception;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String s) {
        super(s);
    }
}
