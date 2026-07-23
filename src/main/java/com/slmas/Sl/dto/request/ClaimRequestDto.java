package com.slmas.Sl.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ClaimRequestDto {
    private Long userId;
    private String userName;
    private LocalDate date;
    private LocalDateTime createdAt;
    private String title;
    private String area;
    private String claimant;
    private String problemType;
    private String description;
    private String solution;
    private List<String> images;
    private String auditUserName;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getClaimant() { return claimant; }
    public void setClaimant(String claimant) { this.claimant = claimant; }
    public String getProblemType() { return problemType; }
    public void setProblemType(String problemType) { this.problemType = problemType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }
    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
    public String getAuditUserName() { return auditUserName; }
    public void setAuditUserName(String auditUserName) { this.auditUserName = auditUserName; }
}
