package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.CompletedWork;
import com.slmas.Sl.repository.CompletedWorkRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public class CompletedWorkRepositoryImpl implements CompletedWorkRepository {
    private final JdbcTemplate jdbcTemplate;

    public CompletedWorkRepositoryImpl(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }

    @Override
    public List<CompletedWork> findByUserIdAndDate(Long userId, LocalDate date) {
        if (date == null) {
            return jdbcTemplate.query("SELECT * FROM CompletedWorks WHERE user_id = ? ORDER BY work_date DESC, title", new Object[]{userId}, rowMapper());
        }
        return jdbcTemplate.query("SELECT * FROM CompletedWorks WHERE user_id = ? AND work_date = ? ORDER BY title", new Object[]{userId, Date.valueOf(date)}, rowMapper());
    }

    @Override
    public CompletedWork create(CompletedWork completedWork) {
        completedWork.setId(UUID.randomUUID().toString());
        completedWork.setDate(LocalDate.now());
        jdbcTemplate.update("INSERT INTO CompletedWorks (id, user_id, user_name, work_date, title, area, description) VALUES (?,?,?,?,?,?,?)",
                completedWork.getId(), completedWork.getUserId(), completedWork.getUserName(), Date.valueOf(completedWork.getDate()),
                completedWork.getTitle(), completedWork.getArea(), completedWork.getDescription());
        return completedWork;
    }

    @Override
    public CompletedWork update(CompletedWork completedWork) {
        jdbcTemplate.update("UPDATE CompletedWorks SET title = ?, area = ?, description = ? WHERE id = ?",
                completedWork.getTitle(), completedWork.getArea(), completedWork.getDescription(), completedWork.getId());
        return jdbcTemplate.queryForObject("SELECT * FROM CompletedWorks WHERE id = ?", rowMapper(), completedWork.getId());
    }

    private RowMapper<CompletedWork> rowMapper() {
        return (rs, rowNum) -> {
            CompletedWork completedWork = new CompletedWork();
            completedWork.setId(rs.getString("id"));
            completedWork.setUserId(rs.getLong("user_id"));
            completedWork.setUserName(rs.getString("user_name"));
            completedWork.setDate(rs.getDate("work_date").toLocalDate());
            completedWork.setTitle(rs.getString("title"));
            completedWork.setArea(rs.getString("area"));
            completedWork.setDescription(rs.getString("description"));
            return completedWork;
        };
    }
}
