package bank.account_movement.dto.request;

import java.math.BigDecimal;

import bank.common_lib.enumeration.MovementType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MovementRegisterRequest {

    @NotNull(message = "Account id is required")
    @Positive(message = " Account id must be positive")
    private Long accountId;

    @NotBlank(message = "Movement type is required")
    private MovementType movementType;

    @NotNull(message = "Value is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Value must be greater than zero")
    private BigDecimal value;
}
