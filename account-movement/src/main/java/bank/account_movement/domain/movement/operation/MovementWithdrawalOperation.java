package bank.account_movement.domain.movement.operation;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import bank.account_movement.domain.movement.validations.MovementValidation;
import bank.common_lib.enumeration.MovementType;

@Component
public class MovementWithdrawalOperation implements MovementOperation{

    private final MovementValidation movementValidation;

    MovementWithdrawalOperation(MovementValidation movementValidation){
        this.movementValidation = movementValidation;
    }

    @Override
    public BigDecimal processMovement(BigDecimal initialBalance, BigDecimal value) {
        
        movementValidation.validatePositiveValue(value);

        movementValidation.validatePositiveBalance(initialBalance);

        movementValidation.validateEnoughBalance(initialBalance, value);

        return initialBalance.subtract(value);
    }

    @Override
    public BigDecimal reverseMovement(BigDecimal initialBalance, BigDecimal value) {
        movementValidation.validateEnoughBalance(initialBalance, value);
        return initialBalance.subtract(value);
    }

    @Override
    public MovementType getMovementType() {
        return MovementType.WITHDRAWAL;
    }
}
