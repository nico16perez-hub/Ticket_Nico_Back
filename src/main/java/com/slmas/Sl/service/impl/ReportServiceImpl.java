package com.slmas.Sl.service.impl;

import com.slmas.Sl.dto.response.ReportResponseDto;
import com.slmas.Sl.repository.ReportRepository;
import com.slmas.Sl.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;

    public ReportServiceImpl(ReportRepository reportRepository) { this.reportRepository = reportRepository; }

    @Override
    public List<ReportResponseDto> getReport(LocalDate from, LocalDate to) {
        return reportRepository.findByDateBetween(from, to);
    }

    @Override
    public List<ReportResponseDto> getReport(LocalDate from, LocalDate to) {
        LocalDate selectedFrom = from == null ? LocalDate.now() : from;
        LocalDate selectedTo = to == null ? selectedFrom : to;
        if (selectedFrom.isAfter(selectedTo)) {
            LocalDate temp = selectedFrom;
            selectedFrom = selectedTo;
            selectedTo = temp;
        }
        return reportRepository.findByDateBetween(selectedFrom, selectedTo);
    }
}
