package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.repository.DailyTaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
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
    public DailyTask findRecurringByUserIdAndDateAndContent(Long userId, LocalDate date, String title, String description) {
        List<DailyTask> matches = jdbcTemplate.query(
                "SELECT * FROM DailyTasks WHERE user_id = ? AND task_date = ? AND type = 'recurrente' AND LOWER(TRIM(title)) = LOWER(TRIM(?)) AND LOWER(TRIM(description)) = LOWER(TRIM(?)) ORDER BY title LIMIT 1",
                new Object[]{userId, Date.valueOf(date), title, description},
                rowMapper()
        );
        return matches.isEmpty() ? null : matches.get(0);
    }

    @Override
    public DailyTask create(DailyTask dailyTask) {
        dailyTask.setId(UUID.randomUUID().toString());
        if (dailyTask.getArea() == null || dailyTask.getArea().isBlank()) dailyTask.setArea("Sistemas");
        jdbcTemplate.update("INSERT INTO DailyTasks (id, user_id, user_name, task_date, type, title, description, area) VALUES (?,?,?,?,?,?,?,?)",
                dailyTask.getId(), dailyTask.getUserId(), dailyTask.getUserName(), Date.valueOf(dailyTask.getDate()), dailyTask.getType(),
                dailyTask.getTitle(), dailyTask.getDescription(), dailyTask.getArea());
        return dailyTask;
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
            return dailyTask;
        };
    }
}
