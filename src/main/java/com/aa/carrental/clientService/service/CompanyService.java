package com.aa.carrental.clientService.service;

import com.aa.carrental.clientService.model.Company;
import com.aa.carrental.clientService.model.Person;
import com.aa.carrental.clientService.repository.CompanyRepository;
import com.aa.carrental.exception.ClientDocExistException;
import com.aa.carrental.exception.ClientPhoneExist;
import com.aa.carrental.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.aa.carrental.clientService.service.ClientValidator.*;
import static com.aa.carrental.clientService.service.ClientValidator.ValidationResults.SUCCESS;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

//    public List<Company> getCompanies(){
//        return companyRepository.findAll();
//    }

    public Page<Company> getCompanies(int page, int size , String keyword, String order , String field){
        return  companyRepository.findAllByPhoneContainingOrFirstnameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                keyword,
                keyword,
                keyword,
                PageRequest.of(page, size, Sort.Direction.valueOf(order),field));
    }
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
    public void addCompany(Company company){
        ClientValidator.ValidationResults results = isNameValid()
                .and(isEmailValid())
                .and(isPhoneValid())
                .apply(company);
        if (results!= SUCCESS)
            throw new IllegalArgumentException(results.name());
        Optional<Company> byPhone = companyRepository.findByPhone(company.getPhone());
        if (byPhone.isPresent())
            throw new ClientPhoneExist("PHONE NUMBER IS ALREADY EXIST");
        companyRepository.save(company);
    }

    public void updateCompany(Company company){
        Company oldCompany = getCompanyById(company.getId());
        Optional<Company> byPhone = companyRepository.findByPhone(company.getPhone());
            if (byPhone.isPresent() && !Objects.equals(oldCompany.getId(), byPhone.get().getId()))
                throw new ClientPhoneExist("PHONE NUMBER IS ALREADY EXIST FOR "+byPhone.get().getFirstname());

        oldCompany.setAddress(company.getAddress());
        oldCompany.setEmail(company.getEmail());
        oldCompany.setFirstname(company.getFirstname());
        oldCompany.setPhone(company.getPhone());
        companyRepository.save(oldCompany);
    }

    public void deleteCompany(Long companyId){
        companyRepository.deleteById(getCompanyById(companyId).getId());
    }

    public Company getCompanyById(Long companyId) {
       return companyRepository.findById(companyId).orElseThrow(
               () -> new ResourceNotFoundException("Company not found!"));
    }
}
