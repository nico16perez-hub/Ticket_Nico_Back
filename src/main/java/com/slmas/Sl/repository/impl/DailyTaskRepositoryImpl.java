package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.repository.DailyTaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class DailyTaskRepositoryImpl implements DailyTaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public DailyTaskRepositoryImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<DailyTask> findByUserIdAndDate(Long userId, LocalDate date) {
        if (date == null) {
            return jdbcTemplate.query("SELECT * FROM DailyTasks WHERE user_id = ? ORDER BY task_date DESC, title", new Object[]{userId}, rowMapper());
        }
        return jdbcTemplate.query("SELECT * FROM DailyTasks WHERE user_id = ? AND task_date = ? ORDER BY title", new Object[]{userId, Date.valueOf(date)}, rowMapper());
    }

    @Override
    public DailyTask findRecurringByUserIdAndDateAndRecurringTaskId(Long userId, LocalDate date, String recurringTaskId) {
        List<DailyTask> matches = jdbcTemplate.query(
                "SELECT * FROM DailyTasks WHERE user_id = ? AND task_date = ? AND type = 'recurrente' AND recurring_task_id = ? ORDER BY title LIMIT 1",
                new Object[]{userId, Date.valueOf(date), recurringTaskId},
                rowMapper()
        );
        return matches.isEmpty() ? null : matches.get(0);
    }

    @Override
    public DailyTask findRecurringByUserIdAndDateAndContent(Long userId, LocalDate date, String title, String description) {
        List<DailyTask> matches = jdbcTemplate.query(
                "SELECT * FROM DailyTasks WHERE user_id = ? AND task_date = ? AND type = 'recurrente' AND LOWER(TRIM(title)) = LOWER(TRIM(?)) AND LOWER(TRIM(COALESCE(description, ''))) = LOWER(TRIM(COALESCE(?, ''))) ORDER BY title LIMIT 1",
                new Object[]{userId, Date.valueOf(date), title, description},
                rowMapper()
        );
        return matches.isEmpty() ? null : matches.get(0);
    }

    @Override
    public DailyTask create(DailyTask dailyTask) {
        dailyTask.setId(UUID.randomUUID().toString());
        if (dailyTask.getArea() == null || dailyTask.getArea().isBlank()) dailyTask.setArea("Sistemas");
        if (dailyTask.getTimestamp() == null) dailyTask.setTimestamp(LocalDateTime.now());
        jdbcTemplate.update("INSERT INTO DailyTasks (id, user_id, user_name, task_date, type, title, description, area, created_at, recurring_task_id) VALUES (?,?,?,?,?,?,?,?,?,?)",
                dailyTask.getId(), dailyTask.getUserId(), dailyTask.getUserName(), Date.valueOf(dailyTask.getDate()), dailyTask.getType(),
                dailyTask.getTitle(), dailyTask.getDescription(), dailyTask.getArea(), Timestamp.valueOf(dailyTask.getTimestamp()), dailyTask.getRecurringTaskId());
        return dailyTask;
    }

    @Override
    public Integer deleteById(String id, Long userId) {
        return jdbcTemplate.update(
                "DELETE FROM DailyTasks WHERE id = ? AND user_id = ? AND type = 'recurrente'",
                id,
                userId
        );
    }

    @Override
    public Integer deleteRecurringByRecurringTaskIdAndDate(String recurringTaskId, Long userId, LocalDate date) {
        return jdbcTemplate.update(
                "DELETE FROM DailyTasks WHERE recurring_task_id = ? AND user_id = ? AND task_date = ? AND type = 'recurrente'",
                recurringTaskId,
                userId,
                Date.valueOf(date)
        );
    }

    private RowMapper<DailyTask> rowMapper() {
        return (rs, rowNum) -> {
            DailyTask dailyTask = new DailyTask();
            dailyTask.setId(rs.getString("id"));
            dailyTask.setUserId(rs.getLong("user_id"));
            dailyTask.setUserName(rs.getString("user_name"));
            dailyTask.setDate(rs.getDate("task_date").toLocalDate());
            dailyTask.setType(rs.getString("type"));
            dailyTask.setTitle(rs.getString("title"));
            dailyTask.setDescription(rs.getString("description"));
            dailyTask.setArea(rs.getString("area"));
            dailyTask.setRecurringTaskId(rs.getString("recurring_task_id"));
            Timestamp timestamp = rs.getTimestamp("created_at");
            dailyTask.setTimestamp(timestamp == null ? null : timestamp.toLocalDateTime());
            return dailyTask;
        };
    }
}
