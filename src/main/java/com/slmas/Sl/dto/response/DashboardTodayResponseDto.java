package com.slmas.Sl.dto.response;

import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.domain.CompletedWork;
import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.domain.RecurringTask;

import java.time.LocalDate;
import java.util.List;

public class DashboardTodayResponseDto {
    private LocalDate date;
    private List<RecurringTaskDashboardItemDto> recurringTasks;
    private List<DailyTask> dailyTasks;
    private List<Claim> claims;
    private List<CompletedWork> completedWorks;

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public List<RecurringTaskDashboardItemDto> getRecurringTasks() { return recurringTasks; }
    public void setRecurringTasks(List<RecurringTaskDashboardItemDto> recurringTasks) { this.recurringTasks = recurringTasks; }
    public List<DailyTask> getDailyTasks() { return dailyTasks; }
    public void setDailyTasks(List<DailyTask> dailyTasks) { this.dailyTasks = dailyTasks; }
    public List<Claim> getClaims() { return claims; }
    public void setClaims(List<Claim> claims) { this.claims = claims; }
    public List<CompletedWork> getCompletedWorks() { return completedWorks; }
    public void setCompletedWorks(List<CompletedWork> completedWorks) { this.completedWorks = completedWorks; }

    public static class RecurringTaskDashboardItemDto {
        private String id;
        private Long userId;
        private String userName;
        private String title;
        private String description;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getUserName() { return userName; }
        public void setUserName(String userName) { this.userName = userName; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}
