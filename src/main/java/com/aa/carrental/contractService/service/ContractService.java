package com.aa.carrental.contractService.service;

import com.aa.carrental.clientService.service.ClientService;
import com.aa.carrental.contractService.model.Contract;
import com.aa.carrental.contractService.model.ContractCodeCounter;
import com.aa.carrental.contractService.repository.ContractCodeRepository;
import com.aa.carrental.contractService.repository.ContractRepository;
import com.aa.carrental.exception.ConflictException;
import com.aa.carrental.exception.ResourceNotFoundException;
import com.aa.carrental.vehicleService.service.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static com.aa.carrental.contractService.service.ContractValidator.*;
import static com.aa.carrental.contractService.service.ContractValidator.ValidationResults.SUCCESS;

/**
 * @author Abdessatar Aliouch
 * @created 25/12/2022
 */
@Transactional
@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final ContractCodeRepository contractCodeRepository;
    private final VehicleService vehicleService;
    private final ClientService clientService;

    public ContractService(ContractRepository contractRepository, ContractCodeRepository contractCodeRepository, VehicleService vehicleService, ClientService clientService) {
        this.contractRepository = contractRepository;
        this.contractCodeRepository = contractCodeRepository;
        this.vehicleService = vehicleService;
        this.clientService = clientService;
    }

    public void deleteContract(Long contractId){
        contractRepository.deleteById(getContractById(contractId).getId());
    }


    public Page<Contract> getContracts(int page, int size, String keyword,String order , String field) {
        return contractRepository.findAllByClient1_PhoneContainingOrClient1_FirstnameContainingIgnoreCaseOrCodeContainingIgnoreCaseOrVehicle_MatriculeContainingIgnoreCase(
                keyword,
                keyword,
                keyword,
                keyword,
                PageRequest.of(page, size, Sort.Direction.valueOf(order),field));
    }

    public void addContract(Contract contract) {
        Contract newContract = new Contract();
        saveContract(contract, newContract);
    }


    public void updateContract(Contract contract) {
        Contract oldContract = getContractById(contract.getId());
        saveContract(contract, oldContract);
    }

    private void saveContract(Contract contract, Contract oldContract) {

        ContractValidator.ValidationResults results =
                isCautionValid()
                        .and(isDatesValid())
                        .and(isAmountPayedValid())
                        .and(isCostPerDayValid())
                        .and(isClientOrVehicleIsInvalid())
                        .apply(contract);
        if (results != SUCCESS)
            throw new RuntimeException("Contract invalid :" + results);

        if (contract.getEndDate() != null) {
            checkForRangeConflict(contract);
            oldContract.setEndDate(contract.getEndDate());
            oldContract.setDuration(ChronoUnit.DAYS.between(contract.getStartDate().toLocalDate(), contract.getEndDate().toLocalDate()));
        } else {
            oldContract.setEndDate(null);
            oldContract.setDuration(0);
        }
        oldContract.setStartDate(contract.getStartDate());
        oldContract.setClient1(clientService.getClientById(contract.getClient1().getId()));

        if (contract.getClient2() != null)
            oldContract.setClient2(clientService.getClientById(contract.getClient2().getId()));
        else oldContract.setClient2(null);

        oldContract.setVehicle(vehicleService.getVehicleById(contract.getVehicle().getId()));

        oldContract.setCautionPayed(contract.isCautionPayed());
        if (oldContract.getCode() == null)
            oldContract.setCode(generateContractCode());
        oldContract.setCaution(contract.getCaution() != null ? contract.getCaution() : 0.0);
        oldContract.setCostPerDay(contract.getCostPerDay() != null ? contract.getCostPerDay() : 0.0);
        oldContract.setAmountPayed(contract.getAmountPayed() != null ? contract.getAmountPayed() : 0.0);

        contractRepository.save(oldContract);
    }

    public Contract getContractById(long contractId) {
        return contractRepository.findById(contractId).orElseThrow(() -> new ResourceNotFoundException("Contract not found"));
    }

    public List<Contract> findAllByClient1_id(long contractId) {
        return contractRepository.findAllByClient1_id(contractId);
    }

    private void checkForRangeConflict(Contract contract) {
        LocalDate startDate = contract.getStartDate().toLocalDate();
        LocalDate endDate = contract.getEndDate().toLocalDate();
        contractRepository.checkIfAnyRangeConflict(
                startDate,
                endDate,
                contract.getVehicle()).ifPresent(contract1 -> {
            if (!Objects.equals(contract1.getId(), contract.getId()))
                throw new ConflictException("Contract overlaps with another contract for the same car. " + contract1.getCode());
        });
    }

    private String generateContractCode() {
        Month value = LocalDate.now().getMonth();
        int year = Year.now().getValue();
        int month = value.getValue();

        ContractCodeCounter contractCodeCounter = contractCodeRepository.findAllByYearAndMonth(year, month).orElse(new ContractCodeCounter());

        String code = "C_" + year + "_" + String.format("%02d", month) + "_" + String.format("%03d", contractCodeCounter.getValue() + 1);

        contractCodeCounter.setYear(year);
        contractCodeCounter.setMonth(month);
        contractCodeCounter.setValue(contractCodeCounter.getValue() + 1);
        contractCodeRepository.save(contractCodeCounter);
        return code;
    }
}
