package bank.account_movement.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import bank.account_movement.service.AccountEventService;
import bank.common_lib.event.dto.client.ClientCreateEvent;

@Component
public class ClientEventConsumer {
    
    private final AccountEventService accountEventService;

    ClientEventConsumer(AccountEventService accountEventService){
        this.accountEventService = accountEventService;
    }
        
    @KafkaListener(topics = "${spring.kafka.topics.client-created}",
                   groupId = "${spring.kafka.consumer.group-id}")
    public void consumeClientCreated(ClientCreateEvent event){
        accountEventService.handleClientCreated(event);
    } 
}
