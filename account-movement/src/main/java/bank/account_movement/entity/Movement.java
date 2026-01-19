package bank.account_movement.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import bank.account_movement.enumerations.MovementType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_movement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movement_seq")
    @SequenceGenerator(name = "movement_seq", sequenceName = "movement_seq", allocationSize = 1)
    @Column(name = "movement_id")
    private Long movementId;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(name = "movement_date", nullable = false)
    private LocalDate movementDate = LocalDate.now();

    @Column(name = "movement_hour", nullable = false)
    private LocalTime movementHour = LocalTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false, length = 15)
    private MovementType movementType;

    @Column(name = "value", nullable = false, precision = 19, scale = 2)
    private BigDecimal value;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;
}
