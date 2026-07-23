package com.slmas.Sl.dto.request;

import java.time.LocalDate;

public class DailyTaskRequestDto {
    private Long userId;
    private String userName;
    private LocalDate date;
    private String type;
    private String title;
    private String description;
    private String area;
    private String recurringTaskId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getRecurringTaskId() { return recurringTaskId; }
    public void setRecurringTaskId(String recurringTaskId) { this.recurringTaskId = recurringTaskId; }
}
