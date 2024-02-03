package com.aa.carrental.contractService.model;




import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author Abdessatar Aliouch
 * @created 25/12/2022
 */
@Getter @Setter
@Entity
public class ContractCodeCounter {
    @Id
    private long id = 1L;
    private int year;
    private int month;
    private int value;


    public ContractCodeCounter(int year, int month, int value) {
        this.year = year;
        this.month = month;
        this.value = value;
    }

    public ContractCodeCounter() {
    }
}
