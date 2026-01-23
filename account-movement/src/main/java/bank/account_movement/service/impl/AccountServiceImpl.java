package bank.account_movement.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import bank.account_movement.dto.request.AccountDeleteRequest;
import bank.account_movement.dto.request.AccountRegisterRequest;
import bank.account_movement.dto.request.AccountUpdateRequest;
import bank.account_movement.dto.response.AccountRegisterResponse;
import bank.account_movement.dto.response.AccountResponse;
import bank.account_movement.dto.response.AccountUpdateResponse;
import bank.account_movement.entity.Account;
import bank.account_movement.mapper.AccountMapper;
import bank.account_movement.repository.AccountRespository;
import bank.account_movement.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

    private final AccountRespository accountRespository;
    private final AccountMapper accountMapper;

    AccountServiceImpl(AccountRespository accountRespository,
                       AccountMapper accountMapper
    ){
        this.accountRespository = accountRespository;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccounts(String clientId) {

        return accountRespository.findByClientIdOrderByAccountNumber(clientId)
            .stream()
            .map(accountMapper::toAccountResponse)
            .toList();

    }

    @Override
    @Transactional
    public AccountRegisterResponse registerAccount(AccountRegisterRequest request) {
        
        String accountType = request.getAccountType().toString();
        findAccountIfPresentThrowExistent(request.getCliendId(), accountType);
        
        Account account = accountMapper.toEntity(request);

        account.setAccountNumber(accountRespository.queryFindNextAccountNumber(accountType));
        
        accountRespository.save(account);
        
        return accountMapper.toAccountRegisterResponse(account);
    }

    @Override
    @Transactional
    public AccountUpdateResponse updateAccount(AccountUpdateRequest request) {

        Account account = findAccountOrElseThrowNotFound(request.getAccountId());

        account.setStatus(request.getStatus());

        Account savedAccount = accountRespository.save(account);

        return accountMapper.toAccountUpdateResponse(savedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(AccountDeleteRequest request) {
        
        findAccountOrElseThrowNotFound(request.getAccountId());
   
        accountRespository.deleteById(request.getAccountId());
    }

    private Account findAccountOrElseThrowNotFound(Long accountId) {
        return accountRespository.findById(accountId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }
    
    private void findAccountIfPresentThrowExistent( String accountId, String accountType){
        accountRespository.queryFindAccountNumber(accountId, accountType)
            .ifPresent( existenceAccount -> {
                throw new ResponseStatusException(
                    HttpStatus.CONFLICT, 
                    "Client already has an "+ accountType +"-"+ existenceAccount + " account");   
            });
    }
}
