package bank.report_service.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ReportResponse {
        private String clientId;
        private String name;
        private String accountNumber;
        private String accountType;
        private String movementType;
        private String value;
        private String balance;
        private LocalDate movementDate;
        private LocalTime movementHour;
}
