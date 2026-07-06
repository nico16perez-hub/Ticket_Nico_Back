package com.slmas.Sl.repository;

import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.exceptions.RepositoryException;

import java.time.LocalDate;

public interface StatisticsRepository {
    Statistics getStatistics(LocalDate startDate, LocalDate endDate) throws RepositoryException;
}
