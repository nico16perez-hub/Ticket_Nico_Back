package com.slmas.Sl.controller;

import com.slmas.Sl.dto.response.ReportResponseDto;
import com.slmas.Sl.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) { this.reportService = reportService; }

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getReport(
            @RequestParam(defaultValue = "today") String period,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        if (startDate != null || endDate != null) {
            LocalDate from = startDate == null ? endDate : startDate;
            LocalDate to = endDate == null ? from : endDate;
            return ResponseEntity.ok(reportService.getReport(from, to));
        }
        return ResponseEntity.ok(reportService.getReport(period));
    }
}
