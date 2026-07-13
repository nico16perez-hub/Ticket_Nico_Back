package com.slmas.Sl.repository;

import com.slmas.Sl.domain.DailyTask;
import java.time.LocalDate;
import java.util.List;

public interface DailyTaskRepository {
    List<DailyTask> findByUserIdAndDate(Long userId, LocalDate date);
    DailyTask findRecurringByUserIdAndDateAndContent(Long userId, LocalDate date, String title, String description);
    DailyTask create(DailyTask dailyTask);
    Integer deleteById(String id, Long userId);
}
