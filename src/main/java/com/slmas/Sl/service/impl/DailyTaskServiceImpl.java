package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.domain.RecurringTask;
import com.slmas.Sl.dto.request.DailyTaskRequestDto;
import com.slmas.Sl.repository.DailyTaskRepository;
import com.slmas.Sl.repository.RecurringTaskRepository;
import com.slmas.Sl.service.DailyTaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DailyTaskServiceImpl implements DailyTaskService {
    private final DailyTaskRepository dailyTaskRepository;
    private final RecurringTaskRepository recurringTaskRepository;

    public DailyTaskServiceImpl(DailyTaskRepository dailyTaskRepository, RecurringTaskRepository recurringTaskRepository) {
        this.dailyTaskRepository = dailyTaskRepository;
        this.recurringTaskRepository = recurringTaskRepository;
    }

    @Override
    public List<DailyTask> findByUserIdAndDate(Long userId, LocalDate date) { return dailyTaskRepository.findByUserIdAndDate(userId, date); }

    @Override
    public DailyTask create(DailyTaskRequestDto request) {
        DailyTask dailyTask = new DailyTask();
        dailyTask.setUserId(request.getUserId());
        dailyTask.setUserName(request.getUserName());
        dailyTask.setDate(request.getDate() == null ? LocalDate.now() : request.getDate());
        dailyTask.setType(request.getType());
        dailyTask.setTitle(request.getTitle());
        dailyTask.setDescription(request.getDescription());
        dailyTask.setArea(request.getArea() == null || request.getArea().isBlank() ? "Sistemas" : request.getArea());
        dailyTask.setTimestamp(LocalDateTime.now());
        dailyTask.setRecurringTaskId(request.getRecurringTaskId());
        if ("recurrente".equalsIgnoreCase(dailyTask.getType())) {
            if (dailyTask.getRecurringTaskId() != null && !dailyTask.getRecurringTaskId().isBlank()) {
                RecurringTask recurringTask = recurringTaskRepository.findById(dailyTask.getRecurringTaskId());
                if (recurringTask != null) {
                    if (dailyTask.getTitle() == null || dailyTask.getTitle().isBlank()) {
                        dailyTask.setTitle(recurringTask.getTitle());
                    }
                    if (dailyTask.getDescription() == null || dailyTask.getDescription().isBlank()) {
                        dailyTask.setDescription(recurringTask.getDescription());
                    }
                }
            }
            DailyTask existing = null;
            if (dailyTask.getRecurringTaskId() != null && !dailyTask.getRecurringTaskId().isBlank()) {
                existing = dailyTaskRepository.findRecurringByUserIdAndDateAndRecurringTaskId(
                        dailyTask.getUserId(),
                        dailyTask.getDate(),
                        dailyTask.getRecurringTaskId()
                );
            }
            if (existing == null) {
                existing = dailyTaskRepository.findRecurringByUserIdAndDateAndContent(
                        dailyTask.getUserId(),
                        dailyTask.getDate(),
                        dailyTask.getTitle(),
                        dailyTask.getDescription()
                );
            }
            if (existing != null) return existing;
        }
        return dailyTaskRepository.create(dailyTask);
    }

    @Override
    public boolean deleteRecurringById(String id, Long userId) {
        return dailyTaskRepository.deleteById(id, userId) > 0;
    }

    @Override
    public boolean deleteRecurringByRecurringTaskIdAndDate(String recurringTaskId, Long userId, LocalDate date) {
        LocalDate selectedDate = date == null ? LocalDate.now() : date;
        return dailyTaskRepository.deleteRecurringByRecurringTaskIdAndDate(recurringTaskId, userId, selectedDate) > 0;
    }
}
