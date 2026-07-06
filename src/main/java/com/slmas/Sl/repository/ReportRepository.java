package com.slmas.Sl.repository;

import com.slmas.Sl.dto.response.ReportResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface ReportRepository {
    List<ReportResponseDto> findByDateBetween(LocalDate from, LocalDate to);
}
