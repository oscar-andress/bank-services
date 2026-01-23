package bank.report_service.service.impl;

import org.springframework.stereotype.Service;

import bank.common_lib.enumeration.AccountType;
import bank.common_lib.event.dto.account.AccountCreateEvent;
import bank.report_service.entity.AccountReport;
import bank.report_service.mapper.ClientReportMapper;
import bank.report_service.repository.AccountReportRepository;
import bank.report_service.service.AccountReportEventService;

@Service
public class AccountReportEventServiceImpl implements AccountReportEventService{

    private final AccountReportRepository accountReportRepository;
    private final ClientReportMapper clientReportMapper;

    AccountReportEventServiceImpl(AccountReportRepository accountReportRepository,
                                  ClientReportMapper clientReportMapper
    ){
        this.accountReportRepository = accountReportRepository;
        this.clientReportMapper = clientReportMapper;
    }

    @Override
    public void handleAccountCreated(AccountCreateEvent accountEvent) {

        hasAccount(accountEvent.getClientId(), accountEvent.getAccountType());

        AccountReport accountReport = clientReportMapper.accountEventToEntity(accountEvent);
        
        accountReportRepository.save(accountReport);
    }

    private boolean hasAccount(String clientId, AccountType accountType){
        return accountReportRepository.findByClientIdAndAccountType(clientId, accountType)
                    .isPresent();
    }
    
}
