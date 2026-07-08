package com.slmas.Sl.controller;

import com.slmas.Sl.domain.CompletedWork;
import com.slmas.Sl.domain.User;
import com.slmas.Sl.dto.request.CompletedWorkRequestDto;
import com.slmas.Sl.service.CompletedWorkService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/completed-works")
public class CompletedWorkController {
    private final CompletedWorkService completedWorkService;

    public CompletedWorkController(CompletedWorkService completedWorkService) { this.completedWorkService = completedWorkService; }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CompletedWork>> findByUserIdAndDate(@PathVariable Long userId,
                                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(completedWorkService.findByUserIdAndDate(userId, date));
    }

    @PostMapping
    public ResponseEntity<CompletedWork> create(@RequestBody CompletedWorkRequestDto request, Authentication authentication) {
        applyAuthenticatedUser(request, authentication);
        return ResponseEntity.ok(completedWorkService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompletedWork> update(@PathVariable String id, @RequestBody CompletedWorkRequestDto request, Authentication authentication) {
        applyEditAudit(request, authentication);
        return ResponseEntity.ok(completedWorkService.update(id, request));
    }

    private void applyAuthenticatedUser(CompletedWorkRequestDto request, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            request.setUserId(user.getId());
            request.setUserName((user.getName() + " " + user.getSurname()).trim());
            if (request.getArea() == null || request.getArea().isBlank()) {
                request.setArea(user.getArea());
            }
        }
    }

    private void applyEditAudit(CompletedWorkRequestDto request, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            request.setEditedBy((user.getName() + " " + user.getSurname()).trim());
            request.setEditedAt(LocalDateTime.now());
        }
    }
}
