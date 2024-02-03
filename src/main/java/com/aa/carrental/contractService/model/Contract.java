package com.aa.carrental.contractService.model;

import com.aa.carrental.clientService.model.Client;
import com.aa.carrental.clientService.model.Person;
import com.aa.carrental.vehicleService.model.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */

@Data
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;


    @NotNull
    @ManyToOne
    private Client client1;

    @ManyToOne
    private Client client2;

    @NotNull
    @ManyToOne
    private Vehicle vehicle;

    @NotNull(message = "start date must not be empty")
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private long duration;

    private Double caution;

    private Double amountPayed;

    private boolean isCautionPayed;

    private Double costPerDay;

    private String insuranceType;

    // private Double finalCost;
    // @Column(columnDefinition = "double default 0.0")
    // private Double discount;
    /*@Column(columnDefinition = "double default 0.0")
    private Double totalCost;*/


}
