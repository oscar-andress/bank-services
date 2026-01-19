package bank.account_movement.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import bank.account_movement.dto.message.event.ClientEvent;
import bank.account_movement.entity.Account;
import bank.account_movement.enumerations.AccountType;
import bank.account_movement.repository.AccountRespository;
import bank.account_movement.service.AccountEventService;

@Service
public class AccountEnventServiceImpl implements AccountEventService{

    private final AccountRespository accountRespository;

    AccountEnventServiceImpl(AccountRespository accountRespository){
        this.accountRespository = accountRespository;
    }

    @Override
    public void handleClientCreated(ClientEvent clientEvent) {

        String clientId = clientEvent.getClientId();
        
        // Has account
        if(hasAccount(clientId, clientEvent.getAccountType())) return;

        // Create default account
        Account account = createDefaultAccount(clientId, clientEvent.getAccountType());

        // Save account
        accountRespository.save(account);
    }

    private boolean hasAccount( String clientId, String accountType){
        return accountRespository.queryFindAccountNumber(clientId, accountType)
            .isPresent();
    }

    private Account createDefaultAccount(String clientId, String accountType){
        
        // Create default account
        Account account = new Account();
        account.setAccountNumber(accountRespository.queryFindNextAccountNumber(accountType));
        account.setAccountType(AccountType.valueOf(accountType));
        account.setClientId(clientId);
        account.setInitialBalance(BigDecimal.ZERO);
        return account;
        
    }

}
