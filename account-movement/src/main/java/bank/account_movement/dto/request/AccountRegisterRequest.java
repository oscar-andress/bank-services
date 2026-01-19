package bank.account_movement.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import bank.account_movement.enumerations.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccountRegisterRequest {
    @NotBlank(message = "Client id is required")
    private String cliendId;

    @NotBlank(message = "Account type is required")
    private AccountType accountType;
    
    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Initial balance must be zero or greater")
    private BigDecimal initialBalance;
}
