package com.aa.carrental.clientService.repository;

import com.aa.carrental.clientService.model.Company;
import com.aa.carrental.clientService.model.Person;
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
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Page<Company> findAllByPhoneContainingOrFirstnameContainingIgnoreCaseOrEmailContainingIgnoreCase(String phone, String firstname, String email, Pageable pageable);
    Optional<Company> findByPhone(String phone);
}
