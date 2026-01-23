package bank.client_person.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import bank.client_person.kafka.config.KafkaTopicsProperties;
import bank.common_lib.event.dto.client.ClientCreateEvent;

@Component
public class ClientEventProducer {
        
    private final KafkaTemplate<String, ClientCreateEvent> kafkaTemplate;
    private final KafkaTopicsProperties kafkaTopicsProperties;

    public ClientEventProducer(KafkaTemplate<String, ClientCreateEvent> kafkaTemplate,
                               KafkaTopicsProperties kafkaTopicsProperties) 
    {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicsProperties = kafkaTopicsProperties;
    }

    public void produceClientEvent(ClientCreateEvent event){
        kafkaTemplate.send(kafkaTopicsProperties.getCreateClient(), event);
    }
}
