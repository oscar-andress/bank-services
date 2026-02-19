package bank.authentication_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.authentication_service.dto.request.ClientCreateRequest;
import bank.authentication_service.dto.response.ClientCreateResponse;
import bank.authentication_service.service.ClientService;

@RestController
@RequestMapping("/bank/api/v1/")
public class ClientController {

    private final ClientService clientService;

    ClientController(ClientService clientService){
        this.clientService = clientService;
    }
    
    @PostMapping("/auth/client")
    public ResponseEntity<ClientCreateResponse> createClient(@RequestBody ClientCreateRequest request) {
        return new ResponseEntity<>(clientService.createClient(request), HttpStatus.CREATED);
    }
}
