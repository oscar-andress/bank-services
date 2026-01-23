package bank.report_service.projection;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReportProjection {
    String getClientId();
    String getName();
    String getAccountNumber();
    String getAccountType();
    String getMovementType();
    String getValue();
    String getBalance();
    LocalDate getMovementDate();
    LocalTime getMovementHour();
}
