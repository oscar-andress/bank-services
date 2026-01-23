package bank.report_service.service;

import java.time.LocalDate;

import bank.report_service.dto.response.PageResponse;
import bank.report_service.dto.response.ReportResponse;

public interface ReportService {
     PageResponse<ReportResponse> getReports(String clientId, LocalDate startDate, LocalDate endDate, int pageNumber, int pageSize);
}
