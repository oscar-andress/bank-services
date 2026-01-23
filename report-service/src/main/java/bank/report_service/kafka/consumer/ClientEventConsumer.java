package bank.report_service.kafka.consumer;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import bank.common_lib.event.dto.client.ClientCreateEvent;
import bank.report_service.service.ClientReportEventService;

@Configuration
public class ClientEventConsumer {

    private ClientReportEventService clientReportEventService;

    ClientEventConsumer(ClientReportEventService clientReportEventService){
        this.clientReportEventService =clientReportEventService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.client-created}",
                   groupId = "${spring.kafka.consumer.group-id}")
    public void consume(ClientCreateEvent event) {
        clientReportEventService.handleClientCreated(event);
    }
    
}
