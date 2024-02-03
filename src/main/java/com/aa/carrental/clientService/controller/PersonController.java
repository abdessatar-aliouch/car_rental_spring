package com.aa.carrental.clientService.controller;

import com.aa.carrental.clientService.model.Person;
import com.aa.carrental.clientService.service.PersonService;
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
@CrossOrigin
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public Page<Person> getPersons(@RequestParam Optional<Integer> page ,
                                   @RequestParam Optional<Integer> size,
                                   @RequestParam Optional<String> keyword,@RequestParam Optional<String> order,@RequestParam Optional<String> field){
        return personService.getPersons(
                page.orElse(0),
                size.orElse(20),
                keyword.orElse(""),
                order.orElse("ASC"),
                field.orElse("id")
        );
    }
    @GetMapping("all")
    public List<Person> getPersons(){
        return personService.getAllPerson();
    }

    @PostMapping
    public ResponseEntity<String> addPerson(@Valid @RequestBody Person person , BindingResult result){
        personService.addPerson(person);
        return ResponseEntity.ok("CLIENT CREATE SUCCESSFULLY");
    }

    @PutMapping
    public ResponseEntity<String> updatePerson(@Valid @RequestBody Person person , BindingResult result){
        ResponseEntity<String> sb = getBindingResult(result);
        if (sb != null) return sb;
        personService.updatePerson(person);
        return ResponseEntity.ok("CLIENT UPDATED SUCCESSFULLY");
    }

    @DeleteMapping("{personId}")
    public ResponseEntity<String> deletePerson(@PathVariable Long personId){
        personService.deletePerson(personId);
        return ResponseEntity.ok("CLIENT DELETED SUCCESSFULLY");
    }

    @GetMapping("{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable Long personId){
        return ResponseEntity.ok(personService.getPersonById(personId));
    }

    /*@PostMapping("/test")
    public ResponseEntity<Person> getPersonTest(){
        return ResponseEntity.ok(personService.getPersonById(1L));
    }*/

}
