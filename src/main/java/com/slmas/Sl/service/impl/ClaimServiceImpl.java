package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.dto.request.ClaimRequestDto;
import com.slmas.Sl.repository.ClaimRepository;
import com.slmas.Sl.service.ClaimService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class ClaimServiceImpl implements ClaimService {
    private final ClaimRepository claimRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository) { this.claimRepository = claimRepository; }

    @Override
    public List<Claim> findByUserIdAndDate(Long userId, LocalDate date) { return claimRepository.findByUserIdAndDate(userId, date); }

    @Override
    public Claim findById(String id) { return claimRepository.findById(id); }

    @Override
    public Claim create(ClaimRequestDto request) {
        Claim claim = new Claim();
        claim.setUserId(request.getUserId());
        claim.setUserName(request.getUserName());
        claim.setTitle(request.getTitle());
        claim.setArea(request.getArea());
        claim.setClaimant(request.getClaimant());
        claim.setProblemType(request.getProblemType());
        claim.setDescription(request.getDescription());
        claim.setSolution(request.getSolution());
        claim.setImages(request.getImages());
        claim.setCreatedBy(pick(request.getAuditUserName(), request.getUserName()));
        claim.setCreatedAt(LocalDateTime.now());
        claim.setEditCount(0);
        claim.setEditHistory(new ArrayList<>());
        claim.setResolutionHistory(new ArrayList<>());
        return claimRepository.create(claim);
    }

    @Override
    public Claim update(String id, ClaimRequestDto request) {
        Claim current = claimRepository.findById(id);
        Claim claim = new Claim();
        claim.setId(id);
        claim.setUserId(current.getUserId());
        claim.setUserName(current.getUserName());
        claim.setDate(current.getDate());
        claim.setCreatedBy(current.getCreatedBy());
        claim.setCreatedAt(current.getCreatedAt());
        claim.setTitle(pick(request.getTitle(), current.getTitle()));
        claim.setArea(pick(request.getArea(), current.getArea()));
        claim.setClaimant(pick(request.getClaimant(), current.getClaimant()));
        claim.setProblemType(pick(request.getProblemType(), current.getProblemType()));
        claim.setDescription(pick(request.getDescription(), current.getDescription()));
        claim.setSolution(request.getSolution() != null ? request.getSolution() : current.getSolution());
        claim.setImages(request.getImages() != null ? request.getImages() : current.getImages());
        String auditUserName = pick(request.getAuditUserName(), current.getUserName());
        LocalDateTime now = LocalDateTime.now();
        claim.setEditedBy(auditUserName);
        claim.setEditedAt(now);
        claim.setEditCount((current.getEditCount() == null ? 0 : current.getEditCount()) + 1);
        List<Claim.ClaimAuditEntry> editHistory = new ArrayList<>(current.getEditHistory() == null ? List.of() : current.getEditHistory());
        editHistory.add(new Claim.ClaimAuditEntry(auditUserName, now.toString()));
        claim.setEditHistory(editHistory);

        boolean wasPending = current.getSolution() == null || current.getSolution().isBlank();
        boolean isResolved = claim.getSolution() != null && !claim.getSolution().isBlank();
        claim.setResolvedBy(current.getResolvedBy());
        claim.setResolvedAt(current.getResolvedAt());
        List<Claim.ClaimAuditEntry> resolutionHistory = new ArrayList<>(current.getResolutionHistory() == null ? List.of() : current.getResolutionHistory());
        if (wasPending && isResolved) {
            claim.setResolvedBy(auditUserName);
            claim.setResolvedAt(now);
            resolutionHistory.add(new Claim.ClaimAuditEntry(auditUserName, now.toString()));
        }
        claim.setResolutionHistory(resolutionHistory);
        return claimRepository.update(claim);
    }

    private String pick(String incoming, String current) {
        return incoming != null && !incoming.isBlank() ? incoming : current;
    }
}
