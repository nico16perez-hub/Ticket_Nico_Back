package com.slmas.Sl.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.domain.CompletedWork;
import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.dto.response.DashboardTodayResponseDto;
import com.slmas.Sl.service.DashboardService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public DashboardServiceImpl(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public DashboardTodayResponseDto getTodaySummary() {
        return getSummary(LocalDate.now());
    }

    @Override
    public DashboardTodayResponseDto getSummary(LocalDate date) {
        LocalDate selectedDate = date == null ? LocalDate.now() : date;

        List<DashboardTodayResponseDto.RecurringTaskDashboardItemDto> recurringTasks = jdbcTemplate.query(
                "SELECT rt.id, rt.user_id, TRIM(CONCAT(u.name, ' ', u.surname)) AS user_name, rt.title, rt.description " +
                "FROM RecurringTasks rt JOIN Users u ON u.id = rt.user_id ORDER BY user_name, rt.title",
                (rs, rowNum) -> {
                    DashboardTodayResponseDto.RecurringTaskDashboardItemDto item = new DashboardTodayResponseDto.RecurringTaskDashboardItemDto();
                    item.setId(rs.getString("id"));
                    item.setUserId(rs.getLong("user_id"));
                    item.setUserName(rs.getString("user_name"));
                    item.setTitle(rs.getString("title"));
                    item.setDescription(rs.getString("description"));
                    return item;
                });

        List<DailyTask> dailyTasks = jdbcTemplate.query(
                "SELECT dt.*, TRIM(CONCAT(u.name, ' ', u.surname)) AS standardized_user_name " +
                "FROM DailyTasks dt JOIN Users u ON u.id = dt.user_id WHERE dt.task_date = ? ORDER BY standardized_user_name, dt.title",
                new Object[]{Date.valueOf(selectedDate)},
                (rs, rowNum) -> {
                    DailyTask dailyTask = new DailyTask();
                    dailyTask.setId(rs.getString("id"));
                    dailyTask.setUserId(rs.getLong("user_id"));
                    dailyTask.setUserName(rs.getString("standardized_user_name"));
                    dailyTask.setDate(rs.getDate("task_date").toLocalDate());
                    dailyTask.setType(rs.getString("type"));
                    dailyTask.setTitle(rs.getString("title"));
                    dailyTask.setDescription(rs.getString("description"));
                    dailyTask.setArea(rs.getString("area"));
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    dailyTask.setTimestamp(createdAt == null ? null : createdAt.toLocalDateTime());
                    return dailyTask;
                });

        List<Claim> claims = jdbcTemplate.query(
                "SELECT c.*, TRIM(CONCAT(u.name, ' ', u.surname)) AS standardized_user_name " +
                "FROM Claims c JOIN Users u ON u.id = c.user_id WHERE c.claim_date = ? ORDER BY standardized_user_name, c.title",
                new Object[]{Date.valueOf(selectedDate)},
                (rs, rowNum) -> {
                    Claim claim = new Claim();
                    claim.setId(rs.getString("id"));
                    claim.setUserId(rs.getLong("user_id"));
                    claim.setUserName(rs.getString("standardized_user_name"));
                    claim.setDate(rs.getDate("claim_date").toLocalDate());
                    claim.setType("reclamo");
                    claim.setTitle(rs.getString("title"));
                    claim.setArea(rs.getString("area"));
                    claim.setClaimant(rs.getString("claimant"));
                    claim.setProblemType(rs.getString("problem_type"));
                    claim.setDescription(rs.getString("description"));
                    claim.setSolution(rs.getString("solution"));
                    claim.setCreatedBy(rs.getString("created_by"));
                    Timestamp createdAt = rs.getTimestamp("created_at");
                    claim.setCreatedAt(createdAt == null ? null : createdAt.toLocalDateTime());
                    claim.setEditedBy(rs.getString("edited_by"));
                    Timestamp editedAt = rs.getTimestamp("edited_at");
                    claim.setEditedAt(editedAt == null ? null : editedAt.toLocalDateTime());
                    claim.setResolvedBy(rs.getString("resolved_by"));
                    Timestamp resolvedAt = rs.getTimestamp("resolved_at");
                    claim.setResolvedAt(resolvedAt == null ? null : resolvedAt.toLocalDateTime());
                    claim.setEditCount(rs.getObject("edit_count") == null ? 0 : rs.getInt("edit_count"));
                    String editHistory = rs.getString("edit_history");
                    try {
                        claim.setEditHistory(editHistory == null ? Collections.emptyList() : objectMapper.readValue(editHistory, new TypeReference<List<Claim.ClaimAuditEntry>>() {}));
                    } catch (Exception e) {
                        claim.setEditHistory(Collections.emptyList());
                    }
                    String resolutionHistory = rs.getString("resolution_history");
                    try {
                        claim.setResolutionHistory(resolutionHistory == null ? Collections.emptyList() : objectMapper.readValue(resolutionHistory, new TypeReference<List<Claim.ClaimAuditEntry>>() {}));
                    } catch (Exception e) {
                        claim.setResolutionHistory(Collections.emptyList());
                    }
                    return claim;
                });

        List<CompletedWork> completedWorks = jdbcTemplate.query(
                "SELECT cw.*, TRIM(CONCAT(u.name, ' ', u.surname)) AS standardized_user_name " +
                "FROM CompletedWorks cw JOIN Users u ON u.id = cw.user_id WHERE cw.work_date = ? ORDER BY standardized_user_name, cw.title",
                new Object[]{Date.valueOf(selectedDate)},
                (rs, rowNum) -> {
                    CompletedWork completedWork = new CompletedWork();
                    completedWork.setId(rs.getString("id"));
                    completedWork.setUserId(rs.getLong("user_id"));
                    completedWork.setUserName(rs.getString("standardized_user_name"));
                    completedWork.setDate(rs.getDate("work_date").toLocalDate());
                    completedWork.setTitle(rs.getString("title"));
                    completedWork.setArea(rs.getString("area"));
                    completedWork.setDescription(rs.getString("description"));
                    completedWork.setSolution(rs.getString("solution"));
                    completedWork.setEditedBy(rs.getString("edited_by"));
                    Timestamp editedAt = rs.getTimestamp("edited_at");
                    completedWork.setEditedAt(editedAt == null ? null : editedAt.toLocalDateTime());
                    return completedWork;
                });

        DashboardTodayResponseDto response = new DashboardTodayResponseDto();
        response.setDate(selectedDate);
        response.setRecurringTasks(recurringTasks);
        response.setDailyTasks(dailyTasks);
        response.setClaims(claims);
        response.setCompletedWorks(completedWorks);
        return response;
    }
}
