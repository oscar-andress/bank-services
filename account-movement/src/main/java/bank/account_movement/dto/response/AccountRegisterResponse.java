package bank.account_movement.dto.response;

import java.math.BigDecimal;

import bank.account_movement.enumerations.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccountRegisterResponse {
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initialBalance;
}
