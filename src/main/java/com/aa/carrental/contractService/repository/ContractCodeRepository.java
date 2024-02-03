package com.aa.carrental.contractService.repository;

import com.aa.carrental.contractService.model.Contract;
import com.aa.carrental.contractService.model.ContractCodeCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Abdessatar Aliouch
 * @created 25/12/2022
 */
@Repository
public interface ContractCodeRepository extends JpaRepository<ContractCodeCounter,Long> {

    Optional<ContractCodeCounter> findAllByYearAndMonth(int year, int month);
}
