package com.slmas.Sl.service;

import com.slmas.Sl.dto.response.DashboardTodayResponseDto;
import java.time.LocalDate;

public interface DashboardService {
    DashboardTodayResponseDto getTodaySummary();
    DashboardTodayResponseDto getSummary(LocalDate date);
}
