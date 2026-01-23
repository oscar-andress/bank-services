package bank.account_movement.domain.movement.operation;

import java.math.BigDecimal;

import bank.common_lib.enumeration.MovementType;

public interface MovementOperation {
    BigDecimal processMovement(BigDecimal initialBalance, BigDecimal value);
    BigDecimal reverseMovement(BigDecimal initialBalance, BigDecimal value);
    MovementType getMovementType();
}
