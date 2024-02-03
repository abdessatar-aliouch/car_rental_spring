package com.aa.carrental.vehicleService.service;

import com.aa.carrental.vehicleService.model.Vehicle;

import java.util.function.Function;

import static com.aa.carrental.vehicleService.service.VehicleValidator.ValidationResult.*;

/**
 * @author Abdessatar Aliouch
 * @created 14/01/2023
 */
public interface VehicleValidator extends Function<Vehicle, VehicleValidator.ValidationResult> {
    enum ValidationResult {
        SUCCESS,
        MATRICULE_INVALID,
        MAKE_INVALID,
        MODEL_INVALID, PRICE_INVALID
    }

    static VehicleValidator isMatriculeValid() {
        return vehicle -> vehicle.getMatricule() != null && !vehicle.getMatricule().isBlank() ? SUCCESS : MATRICULE_INVALID;
    }

    static VehicleValidator isMakeValid() {
        return vehicle -> vehicle.getMake() != null && !vehicle.getMake().isBlank() ? SUCCESS : MAKE_INVALID;
    }

    static VehicleValidator isModelValid() {
        return vehicle -> vehicle.getModel() != null && !vehicle.getModel().isBlank() ? SUCCESS : MODEL_INVALID;
    }
    static VehicleValidator isPriceValid() {
        return vehicle -> vehicle.getPrice() == null || !(vehicle.getPrice()<0) ? SUCCESS : PRICE_INVALID;
    }

    default VehicleValidator and (VehicleValidator other){
        return vehicle -> {
            ValidationResult result = this.apply(vehicle);
           return result.equals(SUCCESS) ? other.apply(vehicle): result;
        };
    }
}

