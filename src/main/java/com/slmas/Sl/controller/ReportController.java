package com.slmas.Sl.controller;

import com.slmas.Sl.dto.response.ReportResponseDto;
import com.slmas.Sl.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) { this.reportService = reportService; }

    @GetMapping
    public ResponseEntity<List<ReportResponseDto>> getReport(@RequestParam(defaultValue = "today") String period) {
        return ResponseEntity.ok(reportService.getReport(period));
    }
}
