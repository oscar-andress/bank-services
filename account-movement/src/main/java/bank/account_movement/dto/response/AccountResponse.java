package bank.account_movement.dto.response;

import java.math.BigDecimal;

import bank.common_lib.enumeration.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccountResponse {
    private Long accountId;
    private String clientId;
    private String accountNumber;
    private AccountType accountType;
    private BigDecimal initialBalance;
    private Boolean status;
}
