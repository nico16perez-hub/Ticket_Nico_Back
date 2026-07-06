package com.slmas.Sl.service;

import com.slmas.Sl.dto.response.DashboardTodayResponseDto;

public interface DashboardService {
    DashboardTodayResponseDto getTodaySummary();
}
