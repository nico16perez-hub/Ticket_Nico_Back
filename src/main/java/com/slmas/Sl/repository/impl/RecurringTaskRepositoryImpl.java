package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.RecurringTask;
import com.slmas.Sl.repository.RecurringTaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class RecurringTaskRepositoryImpl implements RecurringTaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecurringTaskRepositoryImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<RecurringTask> findAll() {
        return jdbcTemplate.query("SELECT * FROM RecurringTasks ORDER BY title", rowMapper());
    }

    @Override
    public RecurringTask findById(String id) {
        List<RecurringTask> matches = jdbcTemplate.query("SELECT * FROM RecurringTasks WHERE id = ? LIMIT 1", new Object[]{id}, rowMapper());
        return matches.isEmpty() ? null : matches.get(0);
    }

    @Override
    public List<RecurringTask> findByUserId(Long userId) {
        return jdbcTemplate.query("SELECT * FROM RecurringTasks WHERE user_id = ? ORDER BY title", new Object[]{userId}, rowMapper());
    }

    @Override
    public RecurringTask create(RecurringTask recurringTask) {
        recurringTask.setId(UUID.randomUUID().toString());
        jdbcTemplate.update("INSERT INTO RecurringTasks (id, user_id, title, description) VALUES (?,?,?,?)",
                recurringTask.getId(), recurringTask.getUserId(), recurringTask.getTitle(), recurringTask.getDescription());
        return recurringTask;
    }

    @Override
    public RecurringTask update(String id, RecurringTask recurringTask) {
        jdbcTemplate.update("UPDATE RecurringTasks SET user_id = ?, title = ?, description = ? WHERE id = ?",
                recurringTask.getUserId(), recurringTask.getTitle(), recurringTask.getDescription(), id);
        recurringTask.setId(id);
        return recurringTask;
    }

    @Override
    public void deleteById(String id) {
        jdbcTemplate.update("UPDATE DailyTasks SET recurring_task_id = NULL WHERE recurring_task_id = ?", id);
        jdbcTemplate.update("DELETE FROM RecurringTasks WHERE id = ?", id);
    }

    private RowMapper<RecurringTask> rowMapper() {
        return (rs, rowNum) -> {
            RecurringTask recurringTask = new RecurringTask();
            recurringTask.setId(rs.getString("id"));
            recurringTask.setUserId(rs.getLong("user_id"));
            recurringTask.setTitle(rs.getString("title"));
            recurringTask.setDescription(rs.getString("description"));
            return recurringTask;
        };
    }
}
