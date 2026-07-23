package com.slmas.Sl.service;

import com.slmas.Sl.dto.response.ReportResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface ReportService {
    List<ReportResponseDto> getReport(LocalDate from, LocalDate to);
}
