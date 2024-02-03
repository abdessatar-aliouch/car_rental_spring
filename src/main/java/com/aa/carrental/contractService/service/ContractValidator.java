package com.aa.carrental.contractService.service;

import com.aa.carrental.contractService.model.Contract;

import java.time.LocalDate;
import java.util.function.Function;

import static com.aa.carrental.contractService.service.ContractValidator.ValidationResults.*;

/**
 * @author Abdessatar Aliouch
 * @created 14/01/2023
 */
public interface ContractValidator extends Function<Contract, ContractValidator.ValidationResults> {

    enum ValidationResults {
        SUCCESS,
        CAUTION_INVALID,
        COST_PER_DAY_INVALID,
        START_DATE_IS_AFTER_END_DATE,
        START_DATE_INVALID,

        AMOUNT_PAYED_INVALID,

        CLIENT1_AND_VEHICLE_IS_REQUIRED

    }

    static ContractValidator isCautionValid() {
        return contract -> {
            if (contract.getCaution() == null) {
               return SUCCESS;
            }
            return contract.getCaution() < 0
                    ? CAUTION_INVALID
                    : SUCCESS;
        };
    }
    static ContractValidator isClientOrVehicleIsInvalid() {
        return contract -> (contract.getClient1()!=null) && (contract.getVehicle() != null)
                ? SUCCESS
                : CLIENT1_AND_VEHICLE_IS_REQUIRED;
    }

    static ContractValidator isCostPerDayValid() {

        return contract -> {
            if (contract.getCostPerDay() == null) {
                return SUCCESS;
            }
           return contract.getCostPerDay() < 0
                    ? COST_PER_DAY_INVALID
                    : SUCCESS;
        };
    }
    static ContractValidator isAmountPayedValid() {
        return contract ->
        {
            if (contract.getAmountPayed() == null) {
                return SUCCESS;
            }
              return  contract.getAmountPayed() < 0
                ? AMOUNT_PAYED_INVALID
                : SUCCESS;
        };
    }

    static ContractValidator isDatesValid() {
        return contract -> {
            if (contract.getEndDate() != null)
                return contract.getStartDate() != null && contract.getStartDate().toLocalDate().isAfter(contract.getEndDate().toLocalDate())
                        ? START_DATE_IS_AFTER_END_DATE
                        : SUCCESS;
            else return contract.getStartDate() == null
                    ? START_DATE_INVALID
                    : SUCCESS;
        };
    }

    default ContractValidator and(ContractValidator other) {
        return contract -> {
            ValidationResults results = this.apply(contract);
            return results.equals(SUCCESS) ? other.apply(contract) : results;
        };
    }
}
