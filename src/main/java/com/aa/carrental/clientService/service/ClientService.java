package com.aa.carrental.clientService.service;

import com.aa.carrental.clientService.model.Client;
import com.aa.carrental.clientService.repository.ClientRepository;
import com.aa.carrental.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Abdessatar Aliouch
 * @created 24/12/2022
 */
@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getClientById(Long clientId) {
       return clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("Client not found!"));
    }
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).get();
    }

}
