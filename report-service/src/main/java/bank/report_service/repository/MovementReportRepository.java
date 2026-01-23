package bank.report_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bank.report_service.entity.MovementReport;

public interface MovementReportRepository extends JpaRepository<MovementReport, Long>{
    Optional<MovementReport> findByMovementId(long movementId);
}
