package com.slmas.Sl.dto.response;

import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.domain.CompletedWork;
import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.domain.RecurringTask;
import com.slmas.Sl.domain.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DashboardTodayResponseDto {
    private LocalDate date;
    private List<RecurringTaskDashboardItemDto> recurringTasks;
    private List<DailyTask> dailyTasks;
    private List<Claim> claims;
    private List<CompletedWork> completedWorks;
    private List<Ticket> tickets;

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
    public List<Ticket> getTickets() { return tickets; }
    public void setTickets(List<Ticket> tickets) { this.tickets = tickets; }

    public static class RecurringTaskDashboardItemDto {
        private String id;
        private Long userId;
        private String userName;
        private String title;
        private String description;
        private boolean completed;
        private String dailyTaskId;
        private LocalDateTime completedAt;

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
        public boolean isCompleted() { return completed; }
        public void setCompleted(boolean completed) { this.completed = completed; }
        public String getDailyTaskId() { return dailyTaskId; }
        public void setDailyTaskId(String dailyTaskId) { this.dailyTaskId = dailyTaskId; }
        public LocalDateTime getCompletedAt() { return completedAt; }
        public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    }
}
