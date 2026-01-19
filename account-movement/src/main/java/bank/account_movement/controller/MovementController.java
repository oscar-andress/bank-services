package bank.account_movement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.account_movement.dto.request.MovementDeleteRequest;
import bank.account_movement.dto.request.MovementRegisterRequest;
import bank.account_movement.dto.response.MovementRegisterResponse;
import bank.account_movement.dto.response.MovementResponse;
import bank.account_movement.dto.response.PageResponse;
import bank.account_movement.service.MovementService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/bank/api/v1")
@CrossOrigin

public class MovementController {

    private final MovementService movementService;

    MovementController(MovementService movementService){
        this.movementService = movementService;
    }

    @PostMapping("/movement")
    public ResponseEntity<MovementRegisterResponse> registerMovement(@RequestBody MovementRegisterRequest request) {
        return new ResponseEntity<>(movementService.registerMovement(request), HttpStatus.CREATED);
    }
    
    @GetMapping("/movement/{accountId}/{pageNumber}/{pageSize}")
    public ResponseEntity<PageResponse<MovementResponse>> getMovements(@PathVariable long accountId, @PathVariable int pageNumber, @PathVariable int pageSize){
        return new ResponseEntity<>(movementService.getMovements(accountId, pageNumber, pageSize), HttpStatus.OK);
    }

    @DeleteMapping("/movement")
    public ResponseEntity<HttpStatus> deleteMovement(@RequestBody MovementDeleteRequest request){
        movementService.deleteMovement(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/movement")
    public ResponseEntity<HttpStatus> updateMovement() {        
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }
}
