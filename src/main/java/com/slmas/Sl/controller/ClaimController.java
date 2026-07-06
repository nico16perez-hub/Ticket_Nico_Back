package com.slmas.Sl.controller;

import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.dto.request.ClaimRequestDto;
import com.slmas.Sl.service.ClaimService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) { this.claimService = claimService; }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Claim>> findByUserIdAndDate(@PathVariable Long userId,
                                                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(claimService.findByUserIdAndDate(userId, date));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Claim> findById(@PathVariable String id) {
        return ResponseEntity.ok(claimService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Claim> create(@RequestBody ClaimRequestDto request) {
        return ResponseEntity.ok(claimService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Claim> update(@PathVariable String id, @RequestBody ClaimRequestDto request) {
        return ResponseEntity.ok(claimService.update(id, request));
    }
}
