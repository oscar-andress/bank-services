package bank.account_movement.service;

import java.util.List;

import bank.account_movement.dto.request.AccountDeleteRequest;
import bank.account_movement.dto.request.AccountRegisterRequest;
import bank.account_movement.dto.request.AccountUpdateRequest;
import bank.account_movement.dto.response.AccountRegisterResponse;
import bank.account_movement.dto.response.AccountResponse;
import bank.account_movement.dto.response.AccountUpdateResponse;

public interface AccountService {
    List<AccountResponse> getAccounts(String clientId);
    AccountRegisterResponse registerAccount(AccountRegisterRequest request);
    AccountUpdateResponse updateAccount(AccountUpdateRequest request);
    void deleteAccount(AccountDeleteRequest request);  
}
