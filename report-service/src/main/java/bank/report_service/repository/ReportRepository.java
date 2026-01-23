package bank.report_service.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import bank.report_service.entity.ClientReport;
import bank.report_service.projection.ReportProjection;

public interface ReportRepository extends JpaRepository<ClientReport, Long>{
    @Query(value = """
            SELECT 
                 c.client_id as clientId,
                 c.name as name,
                 a.account_number as accountNumber,
                 a.account_type as accountType,
                 m.movement_type as movementType,
                 CAST(m.value AS TEXT) as value,
                 CAST(m.balance AS TEXT) as balance,
                 m.movement_date as movementDate,
                 m.movement_hour as movementHour
            FROM tbl_client_report c
            INNER JOIN tbl_account_report a ON c.client_id = a.client_id
            INNER JOIN tbl_movement_report m ON a.account_id = m.account_id
            WHERE c.client_id = :clientId
                  AND m.movement_date BETWEEN :startDate AND :endDate
                  ORDER BY m.movement_date DESC, m.movement_hour DESC, 
                           a.account_type DESC
            """
            , 
            countQuery = """
            SELECT 
                 COUNT(*)
            FROM tbl_client_report c
            INNER JOIN tbl_account_report a ON c.client_id = a.client_id
            INNER JOIN tbl_movement_report m ON a.account_id = m.account_id
            WHERE c.client_id = :clientId
                  AND m.movement_date BETWEEN :startDate AND :endDate
            """
            , nativeQuery = true)
    Page<ReportProjection> queryFindClientMovementsReport(@Param("clientId")String clientId,
                                                          @Param("startDate")LocalDate startDate,
                                                          @Param("endDate")LocalDate endDate,
                                                          Pageable pageable);
}
