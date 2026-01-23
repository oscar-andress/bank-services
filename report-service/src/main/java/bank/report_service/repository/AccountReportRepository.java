package bank.report_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.common_lib.enumeration.AccountType;
import bank.report_service.entity.AccountReport;

public interface AccountReportRepository extends JpaRepository<AccountReport, Long>{
    Optional<AccountReport> findByClientIdAndAccountType(String cliendId, AccountType accountType);
}
