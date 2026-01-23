package bank.report_service.entity;

import bank.common_lib.enumeration.AccountType;
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
@Table(name = "tbl_account_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AccountReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_report_seq")
    @SequenceGenerator(name = "account_report_seq", sequenceName = "account_report_seq", allocationSize = 1)
    @Column(name = "account_report_id")
    private Long accountReportId;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "client_id", nullable = false, length = 10)
    private String clientId;

    @Column(name = "account_number", nullable = false, length = 6)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 15)
    private AccountType accountType;
}
