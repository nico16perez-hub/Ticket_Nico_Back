package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.StatisticsRepository;
import com.slmas.Sl.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public StatisticsServiceImpl(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    @Override
    public Statistics getStatistics(LocalDate startDate, LocalDate endDate) throws RepositoryException {
        if (startDate.isAfter(endDate)) {
            throw new RepositoryException("La fecha de inicio no puede ser mayor que la fecha de fin");
        }
        return statisticsRepository.getStatistics(startDate, endDate);
    }
}
