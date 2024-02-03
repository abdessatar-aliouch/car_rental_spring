package com.aa.carrental.contractService.controller;

import com.aa.carrental.contractService.model.Contract;
import com.aa.carrental.contractService.service.ContractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Abdessatar Aliouch
 * @created 25/12/2022
 */
@CrossOrigin(value = "*")
@RestController
@RequestMapping("/api/contracts")
public class ContractController {

    private final ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping
    public ResponseEntity<String> addContract(@RequestBody Contract contract){
        contractService.addContract(contract);
        return ResponseEntity.ok("CONTRACT ADDED SUCCESSFULLY");
    }

    @PutMapping
    public ResponseEntity<String> updateContract(@RequestBody Contract contract){
        contractService.updateContract(contract);
        return ResponseEntity.ok("CONTRACT UPDATED SUCCESSFULLY");
    }

    @GetMapping
    public Page<Contract> getContracts(@RequestParam Optional<Integer> page ,
                                       @RequestParam Optional<Integer> size ,
                                       @RequestParam Optional<String> keyword,
                                       @RequestParam Optional<String> order ,
                                       @RequestParam Optional<String> field ){

        return contractService.getContracts(
                page.orElse(0),
                size.orElse(20),
                keyword.orElse(""),
                order.orElse("DESC"),
                field.orElse("id")
        );
    }
    @GetMapping("{contractId}")
    public ResponseEntity<Contract> getContract(@PathVariable Long contractId){
        return ResponseEntity.ok(contractService.getContractById(contractId));
    }

    @GetMapping("test/{clientID}")
    public List<Contract> findAllByClient1_id(@PathVariable long clientID) {
        return contractService.findAllByClient1_id(clientID);
    }

    @DeleteMapping("{contractId}")
    public ResponseEntity<String> deleteContract(@PathVariable Long contractId){
        contractService.deleteContract(contractId);
        return ResponseEntity.ok("CONTRACT DELETED SUCCESSFULLY");
    }

}
