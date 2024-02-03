package com.aa.carrental.exception;

import jakarta.validation.constraints.NotNull;

/**
 * @author Abdessatar Aliouch
 * @created 29/04/2023
 */
public class MatriculeAlreadyUsedException extends RuntimeException {
    public MatriculeAlreadyUsedException(@NotNull String message) {
        super(message);
    }
}
