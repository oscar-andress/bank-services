package bank.account_movement.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import bank.account_movement.kafka.config.KafkaTopicsProperties;
import bank.common_lib.event.dto.account.AccountCreateEvent;

@Component
public class AccountEventProducer{

    private final KafkaTemplate<String, AccountCreateEvent> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopicsProperties;

    public AccountEventProducer(KafkaTemplate<String, AccountCreateEvent> kafkaTemplate,
                                KafkaTopicsProperties kafkaTopicsProperties
    ){
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicsProperties = kafkaTopicsProperties;
    }

    public void produceCreateAccount(AccountCreateEvent event) {
        kafkaTemplate.send(kafkaTopicsProperties.getCreateAccount(), event);
    }
    
}
