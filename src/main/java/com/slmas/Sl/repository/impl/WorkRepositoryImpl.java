package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.Ticket;
import com.slmas.Sl.domain.Work;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.WorkRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;
import java.util.Date;

@Repository
public class WorkRepositoryImpl implements WorkRepository {
    JdbcTemplate jdbcTemplate;

    public WorkRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createWork(Work work) throws RepositoryException {
        String CREATE_WORK = "INSERT INTO Works (user_id, user_name, work_date, title, description, image) VALUES (?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(CREATE_WORK, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, work.getUserId());
                ps.setString(2, work.getUserName());
                ps.setTimestamp(3, new Timestamp(work.getDate().getTime()));
                ps.setString(4, work.getTitle());
                ps.setString(5, work.getDescription());
                ps.setBytes(6, work.getImage());
                return ps;
            }, keyHolder);
            Long workId = Objects.requireNonNull(keyHolder.getKey()).longValue();

            String RELATE_USER_WORK = "INSERT INTO UserWorks (user_id, work_id) VALUES (?,?)";
            jdbcTemplate.update(RELATE_USER_WORK, work.getUserId(), workId);
            return workId;
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Work> getUserWorks(Date startDate, Date endDate, Long userId) throws RepositoryException {
        String GET_WORKS = "SELECT * FROM Works WHERE work_date BETWEEN ? AND ? AND user_id = ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endDate = calendar.getTime();
        Object[] params = {new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime()), userId};
        int[] types = {Types.TIMESTAMP, Types.TIMESTAMP, Types.BIGINT};
        try {
            return jdbcTemplate.query(GET_WORKS, params, types, new WorkRepositoryImpl.WorksRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public Work getWorkById(Long id) throws RepositoryException {
        String GET_WORKS = "SELECT * FROM Works WHERE id = ?";
        int[] types = {Types.BIGINT};
        try {
            return jdbcTemplate.queryForObject(GET_WORKS, new Object[]{id}, types, new WorkRepositoryImpl.fullWorkRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Work> getFilteredWorks(Date startDate, Date endDate) throws RepositoryException {
        String GET_FILTERED_WORKS = "SELECT * FROM Works WHERE work_date BETWEEN ? AND ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endDate = calendar.getTime();
        Object[] params = {new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime())};
        int[] types = {Types.TIMESTAMP, Types.TIMESTAMP};
        try {
            return jdbcTemplate.query(GET_FILTERED_WORKS, params, types, new WorkRepositoryImpl.WorksRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Work> downloadWorks(Date startDate, Date endDate) throws RepositoryException {
        String GET_FILTERED_WORKS = "SELECT * FROM Works WHERE work_date BETWEEN ? AND ?";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        endDate = calendar.getTime();
        Object[] params = {new Timestamp(startDate.getTime()), new Timestamp(endDate.getTime())};
        int[] types = {Types.TIMESTAMP, Types.TIMESTAMP};
        try {
            return jdbcTemplate.query(GET_FILTERED_WORKS, params, types, new WorkRepositoryImpl.fullWorkRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Work> getClosedByMeWorks(Date startDate, Date endDate, String solvedBy) throws RepositoryException {
        return List.of();
    }

    @Override
    public Integer editWork(String description, Long workId) throws RepositoryException {
        String EDIT_DESCRIPTION = "UPDATE Works SET description = ? WHERE id = ?";
        try {
            return jdbcTemplate.update(EDIT_DESCRIPTION, description, workId);
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    static class WorksRowMapper implements RowMapper<Work> {
        @Override
        public Work mapRow(ResultSet rs, int rowNum) throws SQLException {
            Work work = new Work();
            work.setId(rs.getLong("id"));
            work.setDate(rs.getTimestamp("work_date"));
            work.setTitle(rs.getString("title"));

            return work;
        }
    }


    static class fullWorkRowMapper implements RowMapper<Work> {
        @Override
        public Work mapRow(ResultSet rs, int rowNum) throws SQLException {
            Work work = new Work();
            work.setId(rs.getLong("id"));
            work.setUserId(rs.getLong("user_id"));
            work.setUserName(rs.getString("user_name"));
            work.setDate(rs.getTimestamp("work_date"));
            work.setTitle(rs.getString("title"));
            work.setDescription(rs.getString("description"));
            work.setImage(rs.getBytes("image"));

            return work;
        }
    }
}
