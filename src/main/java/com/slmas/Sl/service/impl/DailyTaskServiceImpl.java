package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.dto.request.DailyTaskRequestDto;
import com.slmas.Sl.repository.DailyTaskRepository;
import com.slmas.Sl.service.DailyTaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyTaskServiceImpl implements DailyTaskService {
    private final DailyTaskRepository dailyTaskRepository;

    public DailyTaskServiceImpl(DailyTaskRepository dailyTaskRepository) { this.dailyTaskRepository = dailyTaskRepository; }

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
        if ("recurrente".equalsIgnoreCase(dailyTask.getType())) {
            DailyTask existing = dailyTaskRepository.findRecurringByUserIdAndDateAndContent(
                    dailyTask.getUserId(),
                    dailyTask.getDate(),
                    dailyTask.getTitle(),
                    dailyTask.getDescription()
            );
            if (existing != null) return existing;
        }
        return dailyTaskRepository.create(dailyTask);
    }
}
