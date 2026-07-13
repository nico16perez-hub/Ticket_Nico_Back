package com.slmas.Sl.repository.impl;

import com.slmas.Sl.dto.response.ReportResponseDto;
import com.slmas.Sl.repository.ReportRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ReportRepositoryImpl implements ReportRepository {
    private final JdbcTemplate jdbcTemplate;

    public ReportRepositoryImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<ReportResponseDto> findByDateBetween(LocalDate from, LocalDate to) {
        String sql = "SELECT id, task_date as event_date, user_name, type, title, area, description, NULL as solution FROM DailyTasks WHERE task_date BETWEEN ? AND ? " +
                "UNION ALL " +
                "SELECT id, claim_date as event_date, user_name, 'reclamo' as type, title, area, description, solution FROM Claims WHERE claim_date BETWEEN ? AND ? " +
                "UNION ALL " +
                "SELECT id, work_date as event_date, user_name, 'trabajo' as type, title, area, description, solution FROM CompletedWorks WHERE work_date BETWEEN ? AND ? " +
                "ORDER BY event_date DESC";

        return jdbcTemplate.query(sql,
                new Object[]{Date.valueOf(from), Date.valueOf(to), Date.valueOf(from), Date.valueOf(to), Date.valueOf(from), Date.valueOf(to)},
                (rs, rowNum) -> {
                    ReportResponseDto report = new ReportResponseDto();
                    report.setId(rs.getString("id"));
                    report.setDate(rs.getDate("event_date").toLocalDate());
                    report.setUserName(rs.getString("user_name"));
                    report.setType(rs.getString("type"));
                    report.setTitle(rs.getString("title"));
                    report.setArea(rs.getString("area"));
                    report.setDescription(rs.getString("description"));
                    report.setSolution(rs.getString("solution"));
                    return report;
                });
    }
}
