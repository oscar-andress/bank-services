package bank.account_movement.domain.movement.validations;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class MovementValidation {

    public void validateEnoughBalance(BigDecimal initialBalance, BigDecimal value){
        if(initialBalance.compareTo(value) < 0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enougth balance. Current balance: "+ initialBalance );
        }
    }

    public void validatePositiveValue(BigDecimal value){
        if(value.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not allowed zero or negative values");
        }
    }

    public void validatePositiveBalance(BigDecimal initialBalance){
        if(initialBalance.compareTo(BigDecimal.ZERO) <= 0){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Not enough balance. Current balance: " + initialBalance);
        }
    }
}
