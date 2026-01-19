package bank.account_movement.dto.response;

import java.math.BigDecimal;

import bank.account_movement.enumerations.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovementRegisterResponse {
    private Long movementId;
    private MovementType movementType;
    private BigDecimal value;
    private BigDecimal currentBalance;
}
