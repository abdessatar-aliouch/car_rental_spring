package com.aa.carrental.clientService.controller;

import com.aa.carrental.clientService.model.Company;
import com.aa.carrental.clientService.model.Person;
import com.aa.carrental.clientService.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.aa.carrental.clientService.service.IsValid.getBindingResult;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public Page<Company> getCompanies(@RequestParam Optional<Integer> page ,
                                   @RequestParam Optional<Integer> size,
                                   @RequestParam Optional<String> keyword, @RequestParam Optional<String> order, @RequestParam Optional<String> field){
        return companyService.getCompanies(
                page.orElse(0),
                size.orElse(20),
                keyword.orElse(""),
                order.orElse("ASC"),
                field.orElse("id")
        );
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company){
        companyService.addCompany(company);
        return ResponseEntity.ok("COMPANY CREATE SUCCESSFULLY");
    }

    @PutMapping
    public ResponseEntity<String> updateCompany(@Valid @RequestBody Company company ){
        companyService.updateCompany(company);
        return ResponseEntity.ok("COMPANY UPDATED SUCCESSFULLY");
    }
    @GetMapping("all")
    public List<Company> getCompanies(){
        return companyService.getAllCompanies();
    }

    @DeleteMapping("{companyId}")
    public ResponseEntity<String> deleteCompany(@PathVariable Long companyId){
        companyService.deleteCompany(companyId);
        return ResponseEntity.ok("COMPANY DELETED SUCCESSFULLY");
    }

    @GetMapping("{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable Long companyId){
        return ResponseEntity.ok(companyService.getCompanyById(companyId));
    }





}
