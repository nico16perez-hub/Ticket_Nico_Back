package com.slmas.Sl.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Claim {
    private String id;
    private Long userId;
    private String userName;
    private LocalDate date;
    private String type;
    private String title;
    private String area;
    private String claimant;
    private String problemType;
    private String description;
    private String solution;
    private List<String> images;
    private String createdBy;
    private LocalDateTime createdAt;
    private String editedBy;
    private LocalDateTime editedAt;
    private String resolvedBy;
    private LocalDateTime resolvedAt;
    private Integer editCount;
    private List<ClaimAuditEntry> editHistory;
    private List<ClaimAuditEntry> resolutionHistory;

    public static class ClaimAuditEntry {
        private String by;
        private String at;

        public ClaimAuditEntry() {
        }

        public ClaimAuditEntry(String by, String at) {
            this.by = by;
            this.at = at;
        }

        public String getBy() {
            return by;
        }

        public void setBy(String by) {
            this.by = by;
        }

        public String getAt() {
            return at;
        }

        public void setAt(String at) {
            this.at = at;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getClaimant() {
        return claimant;
    }

    public void setClaimant(String claimant) {
        this.claimant = claimant;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public String getResolvedBy() {
        return resolvedBy;
    }

    public void setResolvedBy(String resolvedBy) {
        this.resolvedBy = resolvedBy;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public Integer getEditCount() {
        return editCount;
    }

    public void setEditCount(Integer editCount) {
        this.editCount = editCount;
    }

    public List<ClaimAuditEntry> getEditHistory() {
        return editHistory;
    }

    public void setEditHistory(List<ClaimAuditEntry> editHistory) {
        this.editHistory = editHistory;
    }

    public List<ClaimAuditEntry> getResolutionHistory() {
        return resolutionHistory;
    }

    public void setResolutionHistory(List<ClaimAuditEntry> resolutionHistory) {
        this.resolutionHistory = resolutionHistory;
    }
}
