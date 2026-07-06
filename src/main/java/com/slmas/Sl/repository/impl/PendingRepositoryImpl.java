package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.Pending;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.PendingRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Repository
public class PendingRepositoryImpl implements PendingRepository {

    JdbcTemplate jdbcTemplate;

    public PendingRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer createPending(String notes) throws RepositoryException {
        String CREATE_PENDING = "INSERT INTO Pending (pending_date, notes) VALUES (?,?)";
        Timestamp pendingDate = new Timestamp(new Date().getTime());
        Object[] params = {pendingDate, notes};
        int[] types = {Types.TIMESTAMP, Types.VARCHAR};
        try {
            return jdbcTemplate.update(CREATE_PENDING, params, types);
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public Integer deletePending(Long id) throws RepositoryException {
        String DELETE_PENDING = "UPDATE Pending SET done = true WHERE id=?";
        try {
            return jdbcTemplate.update(DELETE_PENDING, id);
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Pending> getAllPending() throws RepositoryException {
        String GET_ALL_PENDING = "SELECT * FROM Pending WHERE done=false";
        try {
            return jdbcTemplate.query(GET_ALL_PENDING, new PendingRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    static class PendingRowMapper implements RowMapper<Pending> {
        @Override
        public Pending mapRow (ResultSet rs, int rowNum) throws SQLException {
            Pending pending = new Pending();
            pending.setId(rs.getLong("id"));
            pending.setPendingDate(rs.getTimestamp("pending_date"));
            pending.setNotes(rs.getString("notes"));
            pending.setDone(rs.getBoolean("done"));
            return pending;
        }

    }
}
