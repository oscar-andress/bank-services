package bank.account_movement.domain.movement.factory;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import bank.account_movement.domain.movement.operation.MovementOperation;
import bank.common_lib.enumeration.MovementType;

@Component
public class MovementOperationFactory {
    private final Map<MovementType, MovementOperation> operations;

    MovementOperationFactory(List<MovementOperation> operationList){
        this.operations = operationList
                            .stream()
                            .collect(Collectors.toMap(MovementOperation :: getMovementType, 
                                     Function.identity() ));

    }

    public MovementOperation getOperation(MovementType movementType){
        MovementOperation movementOperation = operations.get(movementType);
        if(movementOperation == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Movement type no supported");
        }
        return movementOperation;
    }
}
