package bank.report_service.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bank.report_service.dto.response.PageResponse;
import bank.report_service.dto.response.ReportResponse;
import bank.report_service.service.ReportService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/bank/api/v1/report")
@CrossOrigin

public class ReportController {

    private final ReportService reportService;

    ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping("/{clientId}/{startDate}/{endDate}/{pageNumber}/{pageSize}")
    public ResponseEntity<PageResponse<ReportResponse>> createReport(@PathVariable String clientId,
                               @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                               @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
                               @PathVariable int pageNumber,
                               @PathVariable int pageSize

    ) {
        return new ResponseEntity<>(reportService.getReports(clientId, startDate, endDate, pageNumber, pageSize), HttpStatus.OK);
    }
    
}
