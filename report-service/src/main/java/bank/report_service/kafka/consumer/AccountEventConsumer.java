package bank.report_service.kafka.consumer;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import bank.common_lib.event.dto.account.AccountCreateEvent;
import bank.report_service.service.AccountReportEventService;

@Configuration
public class AccountEventConsumer{

    private AccountReportEventService accountReportEventService;

    AccountEventConsumer(AccountReportEventService accountReportEventService){
        this.accountReportEventService = accountReportEventService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.account-created}",
                   groupId = "${spring.kafka.consumer.group-id}")
    public void consume(AccountCreateEvent event) {
        accountReportEventService.handleAccountCreated(event);
    }
    
}
