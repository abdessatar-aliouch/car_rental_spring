package com.aa.carrental.clientService.service;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
public class IsValid {

    public static ResponseEntity<String> getBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            // Validation errors occurred
            List<FieldError> errors = result.getFieldErrors();
            StringBuilder sb = new StringBuilder();
            for (FieldError error : errors) {
                sb.append("-").append(error.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(sb.toString());
        }
        return null;
    }
}
