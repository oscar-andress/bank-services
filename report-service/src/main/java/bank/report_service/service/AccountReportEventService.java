package bank.report_service.service;

import bank.common_lib.event.dto.account.AccountCreateEvent;

public interface AccountReportEventService {
    void handleAccountCreated(AccountCreateEvent accountEvent);
}
