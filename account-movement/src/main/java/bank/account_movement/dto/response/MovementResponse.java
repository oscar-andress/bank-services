package bank.account_movement.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import bank.common_lib.enumeration.MovementType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovementResponse {
    private Long movementId;
    private Long accountId;
    private LocalDate movementDate = LocalDate.now();
    private LocalTime movementHour = LocalTime.now();
    private MovementType movementType;
    private BigDecimal value;
    private BigDecimal balance;
}
