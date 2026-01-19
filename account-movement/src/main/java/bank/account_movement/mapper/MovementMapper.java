package bank.account_movement.mapper;

import org.springframework.stereotype.Component;

import bank.account_movement.dto.request.MovementRegisterRequest;
import bank.account_movement.dto.response.MovementRegisterResponse;
import bank.account_movement.dto.response.MovementResponse;
import bank.account_movement.entity.Movement;

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
}
