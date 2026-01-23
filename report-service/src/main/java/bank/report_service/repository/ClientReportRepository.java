package bank.report_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.report_service.entity.ClientReport;

public interface ClientReportRepository extends JpaRepository<ClientReport, Long>{
    Optional<ClientReport> findByClientId(String clientId);
}
