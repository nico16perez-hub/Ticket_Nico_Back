package com.slmas.Sl.repository.impl;

import com.slmas.Sl.domain.Guide;
import com.slmas.Sl.exceptions.RepositoryException;
import com.slmas.Sl.repository.GuideRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class GuideRepositoryImpl implements GuideRepository {

    JdbcTemplate jdbcTemplate;

    public GuideRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer createGuide(Guide guide) throws RepositoryException {
        String CREATE_GUIDE = "INSERT INTO Guides (guide_title, guide, admin_only) VALUES (?,?,?)";
        Object[] params = {guide.getTitle(), guide.getGuideText(), guide.isAdminOnly()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN};
        try {
        return jdbcTemplate.update(CREATE_GUIDE, params, types);
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public Integer updateGuide(Guide guide) throws RepositoryException {
        String UPDATE_GUIDE = "UPDATE Guides SET guide_title = ?, guide = ?, admin_only = ? WHERE id = ?";
        Object[] params = {guide.getTitle(), guide.getGuideText(), guide.isAdminOnly(), guide.getId()};
        int[] types = {Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN, Types.BIGINT};
        try {
            return jdbcTemplate.update(UPDATE_GUIDE, params, types);
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Guide> getAllGuides () throws RepositoryException {
        String GET_ALL_GUIDES = "SELECT * FROM Guides";
        try {
            return jdbcTemplate.query(GET_ALL_GUIDES, new GuideRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<Guide> getUserGuides () throws RepositoryException {
        String GET_ALL_GUIDES = "SELECT * FROM Guides WHERE admin_only = false";
        try {
            return jdbcTemplate.query(GET_ALL_GUIDES, new GuideRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public Guide getById (Long id) throws RepositoryException {
        String GET_GUIDE_BY_ID = "SELECT * FROM Guides WHERE id = ?";
        int[] types = {Types.BIGINT};
        try {
            return jdbcTemplate.queryForObject(GET_GUIDE_BY_ID, new Object[]{id}, types, new GuideRowMapper());
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    @Override
    public Integer deleteById (Long id) throws RepositoryException {
        String DELETE_GUIDE = "DELETE FROM Guides WHERE id=?";
        try {
            return jdbcTemplate.update(DELETE_GUIDE, id);
        } catch (Exception e) {
            throw new RepositoryException("Error en base de datos: " + e.getMessage());
        }
    }

    static class GuideRowMapper implements RowMapper<Guide> {
        @Override
        public Guide mapRow(ResultSet rs, int rowNum) throws SQLException {
            Guide guide = new Guide();
            guide.setId(rs.getLong("id"));
            guide.setTitle(rs.getString("guide_title"));
            guide.setGuideText(rs.getString("guide"));
            guide.setAdminOnly(rs.getBoolean("admin_only"));
            return guide;
        }
    }
}
