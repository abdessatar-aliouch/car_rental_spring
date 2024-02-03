package com.aa.carrental.exception;

/**
 * @author Abdessatar Aliouch
 * @created 14/01/2023
 */
public class ClientPhoneExist extends RuntimeException {
    public ClientPhoneExist(String s) {
        super(s);
    }
}
