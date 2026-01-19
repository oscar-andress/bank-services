package bank.account_movement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.account_movement.dto.request.AccountDeleteRequest;
import bank.account_movement.dto.request.AccountRegisterRequest;
import bank.account_movement.dto.request.AccountUpdateRequest;
import bank.account_movement.dto.response.AccountRegisterResponse;
import bank.account_movement.dto.response.AccountResponse;
import bank.account_movement.dto.response.AccountUpdateResponse;
import bank.account_movement.service.AccountService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/bank/api/v1")
@CrossOrigin

public class AccountController {

    private final AccountService accountService;

    AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/account/{clientId}")
    public ResponseEntity<List<AccountResponse>> getAccount(@PathVariable String clientId) {
        return new ResponseEntity<>(accountService.getAccounts(clientId), HttpStatus.OK);
    }

    @PostMapping("/account")
    public ResponseEntity<AccountRegisterResponse> createAccount(@RequestBody AccountRegisterRequest request) {
        return new ResponseEntity<>(accountService.registerAccount(request), HttpStatus.OK);
    }

    @PutMapping("/account")
    public ResponseEntity<AccountUpdateResponse> updateAccount(@RequestBody AccountUpdateRequest request) {
        return new ResponseEntity<>(accountService.updateAccount(request), HttpStatus.OK);
    }

    @DeleteMapping("/account")
    public ResponseEntity<HttpStatus> deleteAccount(@RequestBody AccountDeleteRequest request) {
        accountService.deleteAccount(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
