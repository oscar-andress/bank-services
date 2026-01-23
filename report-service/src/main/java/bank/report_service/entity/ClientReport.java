package bank.report_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_client_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_report_seq")
    @SequenceGenerator(name = "client_report_seq", sequenceName = "client_report_seq", allocationSize = 1)
    @Column(name = "client_report_id")
    private Long clientReportId;

    @Column(name = "client_id", unique = true, length = 10, nullable = false)
    private String clientId;

    @Size (max = 200,  message = "Name must not exceed 200 characters")
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
}
