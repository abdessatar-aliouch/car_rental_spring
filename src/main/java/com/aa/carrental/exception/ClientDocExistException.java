package com.aa.carrental.exception;

/**
 * @author Abdessatar Aliouch
 * @created 02/01/2023
 */
public class ClientDocExistException extends RuntimeException {
    public ClientDocExistException(String message) {
        super(message);
    }
}
