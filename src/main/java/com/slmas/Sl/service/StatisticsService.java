package com.slmas.Sl.service;

import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.exceptions.RepositoryException;

import java.time.LocalDate;

public interface StatisticsService {
    Statistics getStatistics(LocalDate startDate, LocalDate endDate) throws RepositoryException;
}
