package com.slmas.Sl.repository;

import com.slmas.Sl.domain.CompletedWork;
import java.time.LocalDate;
import java.util.List;

public interface CompletedWorkRepository {
    List<CompletedWork> findByUserIdAndDate(Long userId, LocalDate date);
    CompletedWork create(CompletedWork completedWork);
    CompletedWork update(CompletedWork completedWork);
}
