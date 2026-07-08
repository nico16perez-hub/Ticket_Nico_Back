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
        Claim current = claimRepository.findById(id);
        Claim claim = new Claim();
        claim.setId(id);
        claim.setUserId(current.getUserId());
        claim.setUserName(current.getUserName());
        claim.setDate(current.getDate());
        claim.setTitle(pick(request.getTitle(), current.getTitle()));
        claim.setArea(pick(request.getArea(), current.getArea()));
        claim.setClaimant(pick(request.getClaimant(), current.getClaimant()));
        claim.setProblemType(pick(request.getProblemType(), current.getProblemType()));
        claim.setDescription(pick(request.getDescription(), current.getDescription()));
        claim.setSolution(request.getSolution() != null ? request.getSolution() : current.getSolution());
        claim.setImages(request.getImages() != null ? request.getImages() : current.getImages());
        return claimRepository.update(claim);
    }

    private String pick(String incoming, String current) {
        return incoming != null && !incoming.isBlank() ? incoming : current;
    }
}
