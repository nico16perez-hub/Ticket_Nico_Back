package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.CompletedWork;
import com.slmas.Sl.dto.request.CompletedWorkRequestDto;
import com.slmas.Sl.repository.CompletedWorkRepository;
import com.slmas.Sl.service.CompletedWorkService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompletedWorkServiceImpl implements CompletedWorkService {
    private final CompletedWorkRepository completedWorkRepository;

    public CompletedWorkServiceImpl(CompletedWorkRepository completedWorkRepository) { this.completedWorkRepository = completedWorkRepository; }

    @Override
    public List<CompletedWork> findByUserIdAndDate(Long userId, LocalDate date) { return completedWorkRepository.findByUserIdAndDate(userId, date); }

    @Override
    public CompletedWork create(CompletedWorkRequestDto request) {
        CompletedWork completedWork = new CompletedWork();
        completedWork.setUserId(request.getUserId());
        completedWork.setUserName(request.getUserName());
        completedWork.setDate(request.getDate() == null ? LocalDate.now() : request.getDate());
        completedWork.setCreatedAt(request.getCreatedAt() == null ? LocalDateTime.now() : request.getCreatedAt());
        completedWork.setTitle(request.getTitle());
        completedWork.setArea(request.getArea());
        completedWork.setDescription(request.getDescription());
        completedWork.setSolution(request.getSolution());
        completedWork.setEditedBy(request.getEditedBy());
        completedWork.setEditedAt(request.getEditedAt());
        return completedWorkRepository.create(completedWork);
    }

    @Override
    public CompletedWork update(String id, CompletedWorkRequestDto request) {
        CompletedWork completedWork = new CompletedWork();
        completedWork.setId(id);
        completedWork.setTitle(request.getTitle());
        completedWork.setArea(request.getArea());
        completedWork.setDescription(request.getDescription());
        completedWork.setSolution(request.getSolution());
        completedWork.setEditedBy(request.getEditedBy());
        completedWork.setEditedAt(request.getEditedAt());
        return completedWorkRepository.update(completedWork);
    }
}
