package com.slmas.Sl.repository.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.repository.ClaimRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Repository
public class ClaimRepositoryImpl implements ClaimRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public ClaimRepositoryImpl(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Claim> findByUserIdAndDate(Long userId, LocalDate date) {
        if (date == null) {
            return jdbcTemplate.query("SELECT * FROM Claims WHERE user_id = ? ORDER BY claim_date DESC, title", new Object[]{userId}, rowMapper());
        }
        return jdbcTemplate.query("SELECT * FROM Claims WHERE user_id = ? AND claim_date = ? ORDER BY title", new Object[]{userId, Date.valueOf(date)}, rowMapper());
    }

    @Override
    public Claim findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Claims WHERE id = ?", rowMapper(), id);
    }

    @Override
    public Claim create(Claim claim) {
        claim.setId(UUID.randomUUID().toString());
        if (claim.getDate() == null) claim.setDate(LocalDate.now());
        String imagesJson;
        try {
            imagesJson = objectMapper.writeValueAsString(claim.getImages());
        } catch (Exception e) {
            throw new RuntimeException("No se pudieron serializar las imágenes", e);
        }
        String editHistoryJson;
        String resolutionHistoryJson;
        try {
            editHistoryJson = objectMapper.writeValueAsString(claim.getEditHistory());
            resolutionHistoryJson = objectMapper.writeValueAsString(claim.getResolutionHistory());
        } catch (Exception e) {
            throw new RuntimeException("No se pudo serializar la auditoria", e);
        }
        jdbcTemplate.update("INSERT INTO Claims (id, user_id, user_name, claim_date, title, area, claimant, problem_type, description, solution, images, created_by, created_at, edited_by, edited_at, resolved_by, resolved_at, edit_count, edit_history, resolution_history) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                claim.getId(), claim.getUserId(), claim.getUserName(), Date.valueOf(claim.getDate()), claim.getTitle(), claim.getArea(), claim.getClaimant(),
                claim.getProblemType(), claim.getDescription(), claim.getSolution(), imagesJson, claim.getCreatedBy(),
                claim.getCreatedAt() == null ? null : Timestamp.valueOf(claim.getCreatedAt()),
                claim.getEditedBy(), claim.getEditedAt() == null ? null : Timestamp.valueOf(claim.getEditedAt()),
                claim.getResolvedBy(), claim.getResolvedAt() == null ? null : Timestamp.valueOf(claim.getResolvedAt()),
                claim.getEditCount() == null ? 0 : claim.getEditCount(), editHistoryJson, resolutionHistoryJson);
        return claim;
    }

    @Override
    public Claim update(Claim claim) {
        String imagesJson;
        try {
            imagesJson = objectMapper.writeValueAsString(claim.getImages());
        } catch (Exception e) {
            throw new RuntimeException("No se pudieron serializar las imágenes", e);
        }
        String editHistoryJson;
        String resolutionHistoryJson;
        try {
            editHistoryJson = objectMapper.writeValueAsString(claim.getEditHistory());
            resolutionHistoryJson = objectMapper.writeValueAsString(claim.getResolutionHistory());
        } catch (Exception e) {
            throw new RuntimeException("No se pudo serializar la auditoria", e);
        }
        jdbcTemplate.update("UPDATE Claims SET title = ?, area = ?, claimant = ?, problem_type = ?, description = ?, solution = ?, images = ?, edited_by = ?, edited_at = ?, resolved_by = ?, resolved_at = ?, edit_count = ?, edit_history = ?, resolution_history = ? WHERE id = ?",
                claim.getTitle(), claim.getArea(), claim.getClaimant(), claim.getProblemType(), claim.getDescription(), claim.getSolution(), imagesJson,
                claim.getEditedBy(), claim.getEditedAt() == null ? null : Timestamp.valueOf(claim.getEditedAt()),
                claim.getResolvedBy(), claim.getResolvedAt() == null ? null : Timestamp.valueOf(claim.getResolvedAt()),
                claim.getEditCount() == null ? 0 : claim.getEditCount(), editHistoryJson, resolutionHistoryJson, claim.getId());
        return jdbcTemplate.queryForObject("SELECT * FROM Claims WHERE id = ?", rowMapper(), claim.getId());
    }

    private RowMapper<Claim> rowMapper() {
        return (rs, rowNum) -> {
            Claim claim = new Claim();
            claim.setId(rs.getString("id"));
            claim.setUserId(rs.getLong("user_id"));
            claim.setUserName(rs.getString("user_name"));
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
            String images = rs.getString("images");
            try {
                claim.setImages(images == null ? Collections.emptyList() : objectMapper.readValue(images, new TypeReference<List<String>>() {}));
            } catch (Exception e) {
                claim.setImages(Collections.emptyList());
            }
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
        };
    }
}
