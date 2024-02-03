package com.aa.carrental.vehicleService.repository;

import com.aa.carrental.vehicleService.model.Vehicle;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
@Repository
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByMatricule(@NotNull String matricule);
    Page<Vehicle> findAllByMakeContainingIgnoreCaseOrModelContainingIgnoreCaseOrMatriculeContainingIgnoreCase(String make,String model,String matricule, Pageable pageable);

}
