package bank.report_service.service;

import bank.common_lib.event.dto.movement.MovementCreateEvent;
import bank.common_lib.event.dto.movement.MovementDeleteEvent;

public interface MovementReportEventService {
    void handleMovementCreated(MovementCreateEvent movementCreateEvent);
    void handleMovementDeleted(MovementDeleteEvent movementDeleteEvent);
}
