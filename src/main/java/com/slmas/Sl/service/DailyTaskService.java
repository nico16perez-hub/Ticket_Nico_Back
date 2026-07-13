package com.slmas.Sl.service;

import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.dto.request.DailyTaskRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface DailyTaskService {
    List<DailyTask> findByUserIdAndDate(Long userId, LocalDate date);
    DailyTask create(DailyTaskRequestDto request);
    boolean deleteRecurringById(String id, Long userId);
}
