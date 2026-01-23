package bank.report_service.service.impl;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import bank.report_service.dto.response.PageResponse;
import bank.report_service.dto.response.ReportResponse;
import bank.report_service.mapper.ClientReportMapper;
import bank.report_service.projection.ReportProjection;
import bank.report_service.repository.ReportRepository;
import bank.report_service.service.ReportService;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ClientReportMapper clientReportMapper;

    ReportServiceImpl(ReportRepository reportRepository,
                      ClientReportMapper clientReportMapper
    ){
        this.reportRepository = reportRepository;
        this.clientReportMapper = clientReportMapper;
    }

    @Override
    public PageResponse<ReportResponse> getReports(String clientId, LocalDate startDate, LocalDate endDate, int pageNumber, int pageSize) {
        
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        
        Page<ReportProjection> page 
            = reportRepository.queryFindClientMovementsReport(clientId, startDate, endDate, pageRequest);

        return new PageResponse<>(
            page.getContent()
                .stream()
                .map(clientReportMapper::toReportResponse)
                .toList(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages()
        );
    }
    
}
