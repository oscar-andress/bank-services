package bank.account_movement.mapper;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import bank.account_movement.dto.request.MovementRegisterRequest;
import bank.account_movement.dto.response.MovementRegisterResponse;
import bank.account_movement.dto.response.MovementResponse;
import bank.account_movement.entity.Movement;
import bank.common_lib.event.dto.movement.MovementCreateEvent;
import bank.common_lib.event.dto.movement.MovementDeleteEvent;

@Component
public class MovementMapper {
    public Movement toEntity(MovementRegisterRequest request){
        Movement movement = new Movement();
        movement.setAccountId(request.getAccountId());
        movement.setMovementType(request.getMovementType());
        movement.setValue(request.getValue());
        return movement;
    }

    public MovementRegisterResponse toMovementRegisterResponse(Movement movement){
        MovementRegisterResponse response = new MovementRegisterResponse();
        response.setCurrentBalance(movement.getBalance());
        response.setMovementId(movement.getMovementId());
        response.setMovementType(movement.getMovementType());
        response.setValue(movement.getValue());
        return response;
    }

    public MovementResponse toMovementResponse(Movement movement){
        MovementResponse response = new MovementResponse();
        response.setAccountId(movement.getAccountId());
        response.setBalance(movement.getBalance());
        response.setMovementDate(movement.getMovementDate());
        response.setMovementHour(movement.getMovementHour());
        response.setMovementId(movement.getMovementId());
        response.setMovementType(movement.getMovementType());
        response.setValue(movement.getValue());
        return response;
    }

    public MovementCreateEvent toMovementCreateEvent(Movement movement){
        MovementCreateEvent event = new MovementCreateEvent();
        event.setAccountId(movement.getAccountId());
        event.setMovementId(movement.getMovementId());
        event.setInitialBalance(movement.getBalance());
        event.setMovementType(movement.getMovementType());
        event.setValue(movement.getValue());
        event.setMovementDate(movement.getMovementDate());
        event.setMovementHour(movement.getMovementHour());
        return event;
    }

    public MovementDeleteEvent toMovementDeleteEvent(Movement movement, BigDecimal balance){
        MovementDeleteEvent event = new MovementDeleteEvent();
        event.setAccountId(movement.getAccountId());
        event.setMovementId(movement.getMovementId());
        event.setInitialBalance(balance);
        event.setMovementType(movement.getMovementType());
        event.setValue(movement.getValue());
        event.setMovementDate(movement.getMovementDate());
        event.setMovementHour(movement.getMovementHour());
        return event;
    }
}
