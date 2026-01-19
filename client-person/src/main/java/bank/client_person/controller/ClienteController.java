package bank.client_person.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.client_person.dto.request.ClientDeleteRequest;
import bank.client_person.dto.request.ClientUpdateRequest;
import bank.client_person.dto.request.CreateClientRequest;
import bank.client_person.dto.response.ClientResponse;
import bank.client_person.dto.response.ClientUpdateResponse;
import bank.client_person.dto.response.CreateClientResponse;
import bank.client_person.dto.response.PageResponse;
import bank.client_person.service.ClientService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/bank/api/v1")
@CrossOrigin

public class ClienteController {
    
    private ClientService clientService;
    
    ClienteController(ClientService clientService){
        this.clientService = clientService;
    }

    @PostMapping("/client")
    public ResponseEntity<CreateClientResponse> registerClient(@RequestBody CreateClientRequest request) {
        return new ResponseEntity<>(clientService.registerClient(request), HttpStatus.CREATED);
    }

    @GetMapping("/client/{pageSize}/{pageNumber}")
    public ResponseEntity<PageResponse<ClientResponse> > getClients(@PathVariable int pageSize, @PathVariable int pageNumber) {
        return new ResponseEntity<>(clientService.getClients(pageSize, pageNumber), HttpStatus.OK);
    }
    
    @PutMapping("/client")
    public ResponseEntity<ClientUpdateResponse> updateClient(@RequestBody ClientUpdateRequest request) {
        return new ResponseEntity<>(clientService.updateClient(request), HttpStatus.OK);
    }

    @DeleteMapping("/client")
    public ResponseEntity<HttpStatus> deleteClient(@RequestBody ClientDeleteRequest request) {
        clientService.deleteClient(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
