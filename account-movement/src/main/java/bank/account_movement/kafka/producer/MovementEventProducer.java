package bank.account_movement.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import bank.account_movement.kafka.config.KafkaTopicsProperties;
import bank.common_lib.event.dto.movement.MovementCreateEvent;
import bank.common_lib.event.dto.movement.MovementDeleteEvent;
import bank.common_lib.event.dto.movement.MovementEvent;

@Component
public class MovementEventProducer{

    private final KafkaTemplate<String, MovementEvent> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopicsProperties;

    MovementEventProducer(KafkaTemplate<String,MovementEvent> kafkaTemplate,
                          KafkaTopicsProperties kafkaTopicsProperties
    ){
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicsProperties = kafkaTopicsProperties;
    }

    public void produceCreateMovement(MovementCreateEvent event) {
        kafkaTemplate.send(kafkaTopicsProperties.getCreateMovement(), event);
    }

    public void produceDeleteMovement(MovementDeleteEvent event){
        kafkaTemplate.send(kafkaTopicsProperties.getDeleteMovement(), event);
    }
    
}
