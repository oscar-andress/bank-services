package bank.account_movement.mapper;

import org.springframework.stereotype.Component;

import bank.account_movement.dto.request.AccountRegisterRequest;
import bank.account_movement.dto.response.AccountRegisterResponse;
import bank.account_movement.dto.response.AccountResponse;
import bank.account_movement.dto.response.AccountUpdateResponse;
import bank.account_movement.entity.Account;
import bank.common_lib.event.dto.account.AccountCreateEvent;

@Component
public class AccountMapper {
    public AccountResponse toAccountResponse(Account account){
        AccountResponse response = new AccountResponse();
        response.setAccountId(account.getAccountId());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        response.setClientId(account.getClientId());
        response.setInitialBalance(account.getInitialBalance());
        response.setStatus(account.getStatus());
        return response;
    }

    public Account toEntity(AccountRegisterRequest request){
        Account account = new Account();
        account.setAccountType(request.getAccountType());
        account.setClientId(request.getCliendId());
        account.setInitialBalance(request.getInitialBalance());
        return account;
    }

    public AccountRegisterResponse toAccountRegisterResponse(Account account){
        AccountRegisterResponse response = new AccountRegisterResponse();
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType());
        response.setInitialBalance(account.getInitialBalance());
        return response;
    }

    public AccountUpdateResponse toAccountUpdateResponse(Account account){
        AccountUpdateResponse response = new AccountUpdateResponse();
        response.setAccountId(account.getAccountId());
        response.setStatus(account.getStatus());
        return response;
    }

    public AccountCreateEvent toAccountCreateEvent(Account account){
        AccountCreateEvent event = new AccountCreateEvent();
        event.setAccountId(account.getAccountId());
        event.setAccountNumber(account.getAccountNumber());
        event.setAccountType(account.getAccountType());
        event.setClientId(account.getClientId());
        return event;
    }
}
