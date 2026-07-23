package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.RecurringTask;
import com.slmas.Sl.dto.request.RecurringTaskRequestDto;
import com.slmas.Sl.repository.RecurringTaskRepository;
import com.slmas.Sl.service.RecurringTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecurringTaskServiceImpl implements RecurringTaskService {
    private final RecurringTaskRepository recurringTaskRepository;

    public RecurringTaskServiceImpl(RecurringTaskRepository recurringTaskRepository) {
        this.recurringTaskRepository = recurringTaskRepository;
    }

    @Override
    public List<RecurringTask> findAll() { return recurringTaskRepository.findAll(); }

    @Override
    public List<RecurringTask> findByUserId(Long userId) { return recurringTaskRepository.findByUserId(userId); }

    @Override
    public RecurringTask create(RecurringTaskRequestDto request) {
        RecurringTask recurringTask = new RecurringTask();
        recurringTask.setUserId(request.getUserId());
        recurringTask.setTitle(request.getTitle());
        recurringTask.setDescription(request.getDescription());
        return recurringTaskRepository.create(recurringTask);
    }

    @Override
    public RecurringTask update(String id, RecurringTaskRequestDto request) {
        RecurringTask recurringTask = new RecurringTask();
        recurringTask.setUserId(request.getUserId());
        recurringTask.setTitle(request.getTitle());
        recurringTask.setDescription(request.getDescription());
        return recurringTaskRepository.update(id, recurringTask);
    }

    @Override
    public void deleteById(String id) { recurringTaskRepository.deleteById(id); }
}
