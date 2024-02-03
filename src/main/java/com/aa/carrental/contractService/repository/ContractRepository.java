package com.aa.carrental.contractService.repository;

import com.aa.carrental.contractService.model.Contract;
import com.aa.carrental.vehicleService.model.Vehicle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Abdessatar Aliouch
 * @created 25/12/2022
 */
public interface ContractRepository extends JpaRepository<Contract,Long> {

    @Query("SELECT c FROM Contract c where c.vehicle = :vehicle AND (date(c.startDate) BETWEEN :startDate AND :endDate OR date(c.endDate) BETWEEN :startDate AND :endDate)")
    Optional<Contract> checkIfAnyRangeConflict(
            @Param("startDate")LocalDate startDate,
            @Param("endDate")LocalDate endDate,
            @Param("vehicle") Vehicle vehicle);

    List<Contract> findAllByClient1_id(Long client1_id);

    Page<Contract> findAllByClient1_PhoneContainingOrClient1_FirstnameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrVehicle_MatriculeContainingIgnoreCase(String client1_phone, String client1_firstname, String code, String vehicle_matricule, Pageable pageable);
}
