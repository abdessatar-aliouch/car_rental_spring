package com.aa.carrental.clientService.repository;

import com.aa.carrental.clientService.model.Person;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByLicense_Number(String licenseNumber);
    Optional<Person> findByPassport_Number(String PassportNumber);
    Page<Person> findAllByPassport_NumberContainingIgnoreCaseOrLicense_NumberContainingIgnoreCaseOrPhoneContainingOrFirstnameContainingIgnoreCase(String passport_number,String license_number,String phone,String firstname, Pageable pageable);
}
