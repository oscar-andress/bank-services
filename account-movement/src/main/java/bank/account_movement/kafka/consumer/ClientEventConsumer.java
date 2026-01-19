package bank.account_movement.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import bank.account_movement.dto.message.event.ClientEvent;
import bank.account_movement.service.AccountEventService;

@Component
public class ClientEventConsumer {
    
    private final AccountEventService accountEventService;

    ClientEventConsumer(AccountEventService accountEventService){
        this.accountEventService = accountEventService;
    }
        
    @KafkaListener(topics = "${spring.kafka.client-created-topic.name}",
                   groupId = "${spring.kafka.consumer.group-id}")
    public void consumeClientCreated(ClientEvent clientEvent){
        accountEventService.handleClientCreated(clientEvent);
    } 
}
