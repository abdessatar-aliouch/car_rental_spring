package com.aa.carrental.clientService.repository;

import com.aa.carrental.clientService.model.Client;
import com.aa.carrental.clientService.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
