package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.CountEntry;
import com.slmas.Sl.domain.Statistics;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.StatisticsRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StatisticsRepositoryImpl implements StatisticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public StatisticsRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Statistics getStatistics(LocalDate startDate, LocalDate endDate) throws RepositoryException {
        Statistics statistics = new Statistics();
        statistics.setStartDate(startDate);
        statistics.setEndDate(endDate);

        String countClaimsSql = "SELECT COUNT(*) FROM Claims WHERE claim_date BETWEEN ? AND ?";
        String countCompletedWorksSql = "SELECT COUNT(*) FROM CompletedWorks WHERE work_date BETWEEN ? AND ?";
        String countRecurringTasksSql = "SELECT COUNT(*) FROM RecurringTasks";

        String itemsByAreaSql = "SELECT area, COUNT(*) AS count FROM (" +
                " SELECT area FROM Claims WHERE claim_date BETWEEN ? AND ?" +
                " UNION ALL" +
                " SELECT area FROM CompletedWorks WHERE work_date BETWEEN ? AND ?" +
                ") stats GROUP BY area ORDER BY count DESC, area";

        String claimsByProblemTypeSql = "SELECT problem_type, COUNT(*) AS count FROM Claims WHERE claim_date BETWEEN ? AND ? GROUP BY problem_type ORDER BY count DESC, problem_type";

        String itemsByUserSql = "SELECT user_name, COUNT(*) AS count FROM (" +
                " SELECT user_name FROM Claims WHERE claim_date BETWEEN ? AND ?" +
                " UNION ALL" +
                " SELECT user_name FROM CompletedWorks WHERE work_date BETWEEN ? AND ?" +
                ") stats GROUP BY user_name ORDER BY count DESC, user_name";

        String claimsByClaimantSql = "SELECT claimant, COUNT(*) AS count FROM Claims WHERE claim_date BETWEEN ? AND ? GROUP BY claimant ORDER BY count DESC, claimant";

        try {
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);

            Integer totalClaims = jdbcTemplate.queryForObject(countClaimsSql, Integer.class, sqlStartDate, sqlEndDate);
            Integer totalCompletedWorks = jdbcTemplate.queryForObject(countCompletedWorksSql, Integer.class, sqlStartDate, sqlEndDate);
            Integer totalRecurringTasks = jdbcTemplate.queryForObject(countRecurringTasksSql, Integer.class);

            int claims = totalClaims != null ? totalClaims : 0;
            int completedWorks = totalCompletedWorks != null ? totalCompletedWorks : 0;
            int recurringTasks = totalRecurringTasks != null ? totalRecurringTasks : 0;

            List<CountEntry> itemsByRecordType = new ArrayList<>();
            itemsByRecordType.add(new CountEntry("reclamo", claims));
            itemsByRecordType.add(new CountEntry("trabajo", completedWorks));
            itemsByRecordType.add(new CountEntry("tarea recurrente", recurringTasks));

            List<CountEntry> itemsByArea = jdbcTemplate.query(itemsByAreaSql, (rs, rowNum) ->
                    new CountEntry(rs.getString("area"), rs.getInt("count")), sqlStartDate, sqlEndDate, sqlStartDate, sqlEndDate);

            List<CountEntry> claimsByProblemType = jdbcTemplate.query(claimsByProblemTypeSql, (rs, rowNum) ->
                    new CountEntry(rs.getString("problem_type"), rs.getInt("count")), sqlStartDate, sqlEndDate);

            List<CountEntry> itemsByUser = jdbcTemplate.query(itemsByUserSql, (rs, rowNum) ->
                    new CountEntry(rs.getString("user_name"), rs.getInt("count")), sqlStartDate, sqlEndDate, sqlStartDate, sqlEndDate);

            List<CountEntry> claimsByClaimant = jdbcTemplate.query(claimsByClaimantSql, (rs, rowNum) ->
                    new CountEntry(rs.getString("claimant"), rs.getInt("count")), sqlStartDate, sqlEndDate);

            statistics.setItemsByRecordType(itemsByRecordType);
            statistics.setItemsByArea(itemsByArea);
            statistics.setClaimsByProblemType(claimsByProblemType);
            statistics.setItemsByUser(itemsByUser);
            statistics.setClaimsByClaimant(claimsByClaimant);
            statistics.setTotalClaims(claims);
            statistics.setTotalCompletedWorks(completedWorks);
            statistics.setTotalRecurringTasks(recurringTasks);
            statistics.setTotalItems(claims + completedWorks + recurringTasks);

            return statistics;
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }
}
