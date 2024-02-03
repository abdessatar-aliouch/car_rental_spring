package com.aa.carrental.clientService.controller;

import com.aa.carrental.clientService.model.Client;
import com.aa.carrental.clientService.model.Person;
import com.aa.carrental.clientService.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping
    public List<Client> getCompanies(){
        return clientService.getClients();
    }

    @GetMapping("{clientId}")
    public ResponseEntity<Client> getPerson(@PathVariable Long clientId){
        return ResponseEntity.ok(clientService.getClient(clientId));
    }

}
