package com.slmas.Sl.service;

import com.slmas.Sl.dto.response.ReportResponseDto;
import java.util.List;

public interface ReportService {
    List<ReportResponseDto> getReport(String period);
}
