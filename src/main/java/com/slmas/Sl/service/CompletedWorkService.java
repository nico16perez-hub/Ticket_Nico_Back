package com.slmas.Sl.service;

import com.slmas.Sl.domain.CompletedWork;
import com.slmas.Sl.dto.request.CompletedWorkRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface CompletedWorkService {
    List<CompletedWork> findByUserIdAndDate(Long userId, LocalDate date);
    CompletedWork create(CompletedWorkRequestDto request);
    CompletedWork update(String id, CompletedWorkRequestDto request);
}
