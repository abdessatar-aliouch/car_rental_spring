package com.aa.carrental.vehicleService.model;

import com.aa.carrental.enums.VehicleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */

@Data
@Entity
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String make;
    @NotNull
    private String model;
    @NotNull
    @Column(unique = true)
    private String matricule;
    private String color;
    private int year;
    private int engineSize;
    @Lob
    private String imageBase64;
    private String fuelType;
    private Double price;
    private Double caution;
    private int numSeats;
    private String transmissionType;
    private double rentalRate;
    @Enumerated(EnumType.STRING)
    private VehicleStatus status;
    private String description;
    private String insuranceCoverage;
    private LocalDate insuranceExpirationDate;
    private LocalDate visitExpirationDate;
    private List<String> additionalFeatures;

}
