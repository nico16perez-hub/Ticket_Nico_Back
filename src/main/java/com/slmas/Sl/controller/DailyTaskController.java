package com.slmas.Sl.controller;

import com.slmas.Sl.domain.DailyTask;
import com.slmas.Sl.domain.User;
import com.slmas.Sl.dto.request.DailyTaskRequestDto;
import com.slmas.Sl.service.DailyTaskService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/daily-tasks")
public class DailyTaskController {
    private final DailyTaskService dailyTaskService;

    public DailyTaskController(DailyTaskService dailyTaskService) { this.dailyTaskService = dailyTaskService; }

    @GetMapping("/{userId}")
    public ResponseEntity<List<DailyTask>> findByUserIdAndDate(@PathVariable Long userId,
                                                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(dailyTaskService.findByUserIdAndDate(userId, date));
    }

    @PostMapping
    public ResponseEntity<DailyTask> create(@RequestBody DailyTaskRequestDto request, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            request.setUserId(user.getId());
            request.setUserName((user.getName() + " " + user.getSurname()).trim());
            request.setArea(user.getArea());
        }
        return ResponseEntity.ok(dailyTaskService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            boolean deleted = dailyTaskService.deleteRecurringById(id, user.getId());
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(401).build();
    }

    @DeleteMapping("/recurring/{recurringTaskId}")
    public ResponseEntity<Void> deleteRecurringByDate(@PathVariable String recurringTaskId,
                                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                      Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            boolean deleted = dailyTaskService.deleteRecurringByRecurringTaskIdAndDate(recurringTaskId, user.getId(), date);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(401).build();
    }
}
