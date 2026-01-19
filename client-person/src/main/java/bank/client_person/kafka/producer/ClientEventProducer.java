package bank.client_person.kafka.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import bank.client_person.dto.message.event.ClientEvent;


@Component
public class ClientEventProducer {
        
    private final KafkaTemplate<String, ClientEvent> kafkaTemplate;
    private String crateClientTopic;

    public ClientEventProducer(KafkaTemplate<String, ClientEvent> kafkaTemplate,
                               @Value("${spring.kafka.create-client-topic.name}") String crateClientTopic) 
    {
        this.crateClientTopic = crateClientTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(ClientEvent clientEvent){
        // Create client event
        kafkaTemplate.send(crateClientTopic, clientEvent);
    }
}
