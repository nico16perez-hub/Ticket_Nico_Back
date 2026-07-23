package com.slmas.Sl.service;

import com.slmas.Sl.domain.RecurringTask;
import com.slmas.Sl.dto.request.RecurringTaskRequestDto;
import java.util.List;

public interface RecurringTaskService {
    List<RecurringTask> findAll();
    List<RecurringTask> findByUserId(Long userId);
    RecurringTask create(RecurringTaskRequestDto request);
    RecurringTask update(String id, RecurringTaskRequestDto request);
    void deleteById(String id);
}
