package bank.report_service.mapper;

import org.springframework.context.annotation.Configuration;

import bank.common_lib.enumeration.MovementType;
import bank.common_lib.event.dto.account.AccountCreateEvent;
import bank.common_lib.event.dto.client.ClientCreateEvent;
import bank.common_lib.event.dto.movement.MovementCreateEvent;
import bank.common_lib.event.dto.movement.MovementDeleteEvent;
import bank.report_service.dto.response.ReportResponse;
import bank.report_service.entity.AccountReport;
import bank.report_service.entity.ClientReport;
import bank.report_service.entity.MovementReport;
import bank.report_service.projection.ReportProjection;

@Configuration
public class ClientReportMapper {
    public ClientReport clientEventToEntity(ClientCreateEvent event){
        ClientReport clientReport = new ClientReport();
        clientReport.setClientId(event.getClientId());
        clientReport.setName(event.getName());
        return clientReport;
    }

    public AccountReport accountEventToEntity(AccountCreateEvent event){
        AccountReport accountReport = new AccountReport();
        accountReport.setAccountId(event.getAccountId());
        accountReport.setAccountNumber(event.getAccountNumber());
        accountReport.setAccountType(event.getAccountType());
        accountReport.setClientId(event.getClientId());
        return accountReport;
    }

    public MovementReport movementCreateEventToEntity(MovementCreateEvent event){
        MovementReport movementReport = new MovementReport();
        movementReport.setAccountId(event.getAccountId());
        movementReport.setBalance(event.getInitialBalance());
        movementReport.setMovementDate(event.getMovementDate());
        movementReport.setMovementHour(event.getMovementHour());
        movementReport.setMovementId(event.getMovementId());
        movementReport.setMovementType(event.getMovementType());
        movementReport.setValue(event.getValue());
        return movementReport;
    }

    public MovementReport movementDeleteEventToEntity(MovementDeleteEvent event){
        MovementReport movementReport = new MovementReport();
        movementReport.setAccountId(event.getAccountId());
        movementReport.setBalance(event.getInitialBalance());
        movementReport.setMovementDate(event.getMovementDate());
        movementReport.setMovementHour(event.getMovementHour());
        movementReport.setMovementId(event.getMovementId());
        movementReport.setMovementType(MovementType.REVERSE);
        movementReport.setValue(event.getValue());
        return movementReport;
    }

    public ReportResponse toReportResponse(ReportProjection projection){
        ReportResponse response = new ReportResponse();
        response.setClientId(projection.getClientId());
        response.setName(projection.getName());
        response.setAccountNumber(projection.getAccountNumber());
        response.setAccountType(projection.getAccountType());
        response.setMovementType(projection.getMovementType());
        response.setValue(projection.getValue());
        response.setBalance(projection.getBalance());
        response.setMovementDate(projection.getMovementDate());
        response.setMovementHour(projection.getMovementHour());
        return response;
    }
}
