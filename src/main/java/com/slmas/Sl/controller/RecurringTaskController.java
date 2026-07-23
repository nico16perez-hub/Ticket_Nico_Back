package com.slmas.Sl.controller;

import com.slmas.Sl.domain.User;
import com.slmas.Sl.domain.RecurringTask;
import com.slmas.Sl.dto.request.RecurringTaskRequestDto;
import com.slmas.Sl.service.RecurringTaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recurring-tasks")
public class RecurringTaskController {
    private final RecurringTaskService recurringTaskService;

    public RecurringTaskController(RecurringTaskService recurringTaskService) { this.recurringTaskService = recurringTaskService; }

    @GetMapping
    public ResponseEntity<List<RecurringTask>> findAll() {
        return ResponseEntity.ok(recurringTaskService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<RecurringTask>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(recurringTaskService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<RecurringTask> create(@RequestBody RecurringTaskRequestDto request, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            request.setUserId(user.getId());
        }
        return ResponseEntity.ok(recurringTaskService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecurringTask> update(@PathVariable String id, @RequestBody RecurringTaskRequestDto request, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            request.setUserId(user.getId());
        }
        return ResponseEntity.ok(recurringTaskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        recurringTaskService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
