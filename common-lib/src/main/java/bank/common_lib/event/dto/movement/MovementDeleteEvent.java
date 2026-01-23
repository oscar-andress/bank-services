package bank.common_lib.event.dto.movement;

import java.math.BigDecimal;

public class MovementDeleteEvent extends MovementEvent {
    private BigDecimal value;
    private BigDecimal initialBalance;

    public MovementDeleteEvent() {
    }

    public MovementDeleteEvent( BigDecimal value, BigDecimal initialBalance) {
        this.value = value;
        this.initialBalance = initialBalance;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
