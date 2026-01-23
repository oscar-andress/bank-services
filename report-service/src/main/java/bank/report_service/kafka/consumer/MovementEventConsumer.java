package bank.report_service.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import bank.common_lib.event.dto.movement.MovementCreateEvent;
import bank.common_lib.event.dto.movement.MovementDeleteEvent;
import bank.report_service.service.MovementReportEventService;

@Component
public class MovementEventConsumer {

    private final MovementReportEventService movementReportEventService;

    MovementEventConsumer(MovementReportEventService movementReportEventService){
        this.movementReportEventService = movementReportEventService;
    }

    @KafkaListener(topics = "${spring.kafka.topics.movement-created}",
                   groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMovementCreate(MovementCreateEvent event) {
        movementReportEventService.handleMovementCreated(event);
    }
    
    @KafkaListener(topics = "${spring.kafka.topics.movement-deleted}",
                   groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMovementDelete(MovementDeleteEvent event){
        movementReportEventService.handleMovementDeleted(event);
    }
}
