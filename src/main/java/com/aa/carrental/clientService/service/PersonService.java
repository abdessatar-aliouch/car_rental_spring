package com.aa.carrental.clientService.service;

import com.aa.carrental.clientService.model.Person;
import com.aa.carrental.clientService.repository.PersonRepository;
import com.aa.carrental.exception.ClientDocExistException;
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
public class PersonService {
    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Page<Person> getPersons(int page, int size , String keyword,String order , String field){
      return  personRepository.findAllByPassport_NumberContainingIgnoreCaseOrLicense_NumberContainingIgnoreCaseOrPhoneContainingOrFirstnameContainingIgnoreCase(
              keyword,
              keyword,
              keyword,
              keyword,
              PageRequest.of(page, size, Sort.Direction.valueOf(order),field));
    }
    public List<Person> getAllPerson(){
        return personRepository.findAll();
    }
    public void addPerson(Person person){
        isPersonInputValid(person);

        Optional<Person> byLicense_number = getByLicense_number(person);
        Optional<Person> byPassport_number = getByPassport_number(person);
        if (byPassport_number.isPresent())
            throw new ClientDocExistException("PASSPORT NUMBER IS ALREADY EXIST");
        if (byLicense_number.isPresent())
            throw new ClientDocExistException("LICENSE NUMBER IS ALREADY EXIST");

        personRepository.save(person);
    }


    public void updatePerson(Person person){
        isPersonInputValid(person);
        isClientDocValid(person);
        
        Person oldPerson = getPersonById(person.getId());
        oldPerson.setAddress(person.getAddress());
        oldPerson.setDob(person.getDob());
        oldPerson.setEmail(person.getEmail());
        oldPerson.setLastname(person.getLastname());
        oldPerson.setFirstname(person.getFirstname());

        oldPerson.getLicense().setCountry(person.getLicense().getCountry());
        oldPerson.getLicense().setNumber(person.getLicense().getNumber());
        oldPerson.getLicense().setCountry(person.getLicense().getCountry());
        oldPerson.getLicense().setExpirationDate(person.getLicense().getExpirationDate());

        oldPerson.getPassport().setCountry(person.getPassport().getCountry());
        oldPerson.getPassport().setNumber(person.getPassport().getNumber());
        oldPerson.getPassport().setCountry(person.getPassport().getCountry());
        oldPerson.getPassport().setExpirationDate(person.getPassport().getExpirationDate());

        oldPerson.setPhone(person.getPhone());
        personRepository.save(oldPerson);
    }
    public void deletePerson(Long personId){
        personRepository.deleteById(getPersonById(personId).getId());
    }

    public Person getPersonById(Long personId) {
        return personRepository.findById(personId).orElseThrow(
                () -> new ResourceNotFoundException("Person not found!"));
    }

    private static void isPersonInputValid(Person person) {
        ValidationResults results = isNameValid()
                .and(isEmailValid())
                .and(isLicenseNumberValid())
                .and(isLicenseExpirationDateValid())
                .and(isPassportNumberValid())
                .and(isPassportExpirationDateValid())
                .and(isPhoneValid())
                .apply(person);
        if (results!= SUCCESS)
            throw new IllegalArgumentException(results.name());
    }

    private Optional<Person> getByPassport_number(Person person) {
        return personRepository.findByPassport_Number(person.getPassport().getNumber());
    }
    private Optional<Person> getByLicense_number(Person person) {
        return personRepository.findByLicense_Number(person.getLicense().getNumber());
    }
    private void isClientDocValid(Person person) {
        Optional<Person> byLicense_number = getByLicense_number(person);
        Optional<Person> byPassport_number = getByPassport_number(person);

        if (byPassport_number.isPresent() && !Objects.equals(person.getId(), byPassport_number.get().getId()))
            throw new ClientDocExistException("PASSPORT NUMBER IS ALREADY EXIST");

        if (byLicense_number.isPresent() && !Objects.equals(person.getId(), byLicense_number.get().getId()))
            throw new ClientDocExistException("LICENSE NUMBER IS ALREADY EXIST");
    }


}
