package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.dto.request.ClaimRequestDto;
import com.slmas.Sl.repository.ClaimRepository;
import com.slmas.Sl.service.ClaimService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        return claimRepository.create(claim);
    }

    @Override
    public Claim update(String id, ClaimRequestDto request) {
        Claim claim = new Claim();
        claim.setId(id);
        claim.setTitle(request.getTitle());
        claim.setArea(request.getArea());
        claim.setClaimant(request.getClaimant());
        claim.setProblemType(request.getProblemType());
        claim.setDescription(request.getDescription());
        claim.setSolution(request.getSolution());
        claim.setImages(request.getImages());
        return claimRepository.update(claim);
    }
}
