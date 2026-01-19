package bank.account_movement.service;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountOperationService {

    public BigDecimal processDeposit(BigDecimal initialBalance, BigDecimal value){
        validatePositiveValue(value);
        return initialBalance.add(value);
    }

    public BigDecimal processWithdrawal(BigDecimal initialBalance, BigDecimal value){

        validatePositiveValue(value);

        // No balance (0) and withdral
        if(initialBalance.equals(BigDecimal.ZERO)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enought balance to withdrawal");
        }

        // Initial balance < withdrawal 
        if(initialBalance.compareTo(value) < 0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enought balance to withdrawal");
        }

        // Substract the withdrawal value
        return initialBalance.subtract(value);
    }

    public BigDecimal reverseDeposit(BigDecimal initialBalance, BigDecimal value){
        validateEnougthBalanceToReverse(initialBalance, value);
        return initialBalance.subtract(value);
    }

    public BigDecimal reverseWithdrawal(BigDecimal initialBalance, BigDecimal value){
        return initialBalance.add(value);
    }

    private void validateEnougthBalanceToReverse(BigDecimal initialBalance, BigDecimal value){
        if(initialBalance.compareTo(value) < 0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enougth balance to reverse. Current balance: "+ initialBalance );
        }
    }

    private void validatePositiveValue(BigDecimal value){
        // 0 or negative withdrawal values
        if(value.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not allowed 0 withdrawal values");
        }
    }
}
