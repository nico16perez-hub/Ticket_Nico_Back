package com.slmas.Sl.repository;

import com.slmas.Sl.domain.RecurringTask;
import java.util.List;

public interface RecurringTaskRepository {
    List<RecurringTask> findByUserId(Long userId);
    RecurringTask create(RecurringTask recurringTask);
    void deleteById(String id);
}
