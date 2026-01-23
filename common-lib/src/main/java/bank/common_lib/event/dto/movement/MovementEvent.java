package bank.common_lib.event.dto.movement;

import java.time.LocalDate;
import java.time.LocalTime;

import bank.common_lib.enumeration.MovementType;

public class MovementEvent {
    private Long accountId;
    private Long movementId;
    private MovementType movementType;
    private LocalDate movementDate;
    private LocalTime movementHour;

    public MovementEvent() {
    }

    public MovementEvent(Long accountId, Long movementId, MovementType movementType,
                         LocalDate movementDate, LocalTime movemenHour) {
        this.accountId = accountId;
        this.movementId = movementId;
        this.movementType =movementType;
        this.movementDate = movementDate;
        this.movementHour = movemenHour;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setMovementId(Long movementId){
        this.movementId = movementId;
    }

    public Long getMovementId(){
        return movementId;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public LocalDate getMovementDate(){
        return movementDate;
    }

    public void setMovementDate(LocalDate movementDate){
        this.movementDate = movementDate;
    }

    public void setMovementHour(LocalTime movementHour){
        this.movementHour = movementHour;
    }

    public LocalTime getMovementHour(){
        return movementHour;
    }
}
