package bank.account_movement.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import bank.account_movement.entity.Account;
import bank.account_movement.kafka.producer.AccountEventProducer;
import bank.account_movement.mapper.AccountMapper;
import bank.account_movement.repository.AccountRespository;
import bank.account_movement.service.AccountEventService;
import bank.common_lib.enumeration.AccountType;
import bank.common_lib.event.dto.account.AccountCreateEvent;
import bank.common_lib.event.dto.client.ClientCreateEvent;

@Service
public class AccountEnventServiceImpl implements AccountEventService{

    private final AccountRespository accountRespository;
    private final AccountEventProducer accountEventProducer;
    private final AccountMapper accountMapper;

    AccountEnventServiceImpl(AccountRespository accountRespository,
                             AccountEventProducer accountEventProducer,
                             AccountMapper accountMapper
    ){
        this.accountRespository = accountRespository;
        this.accountEventProducer = accountEventProducer;
        this.accountMapper = accountMapper;
    }

    @Override
    public void handleClientCreated(ClientCreateEvent clientEvent) {

        String clientId = clientEvent.getClientId();
        
        if(hasAccount(clientId, clientEvent.getAccountType().toString())) return;

        Account account = createDefaultAccount(clientId, clientEvent.getAccountType());

        Account savedAccount = accountRespository.save(account);

        AccountCreateEvent accountEvent = accountMapper.toAccountCreateEvent(savedAccount);

        accountEventProducer.produceCreateAccount(accountEvent);
    }

    private boolean hasAccount( String clientId, String accountType){
        return accountRespository.queryFindAccountNumber(clientId, accountType)
            .isPresent();
    }

    private Account createDefaultAccount(String clientId, AccountType accountType){
        
        Account account = new Account();
        account.setAccountNumber(accountRespository.queryFindNextAccountNumber(accountType.name()));
        account.setAccountType(accountType);
        account.setClientId(clientId);
        account.setInitialBalance(BigDecimal.ZERO);
        return account;
        
    }

}
