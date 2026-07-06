package com.slmas.Sl.service;

import com.slmas.Sl.domain.Claim;
import com.slmas.Sl.dto.request.ClaimRequestDto;
import java.time.LocalDate;
import java.util.List;

public interface ClaimService {
    List<Claim> findByUserIdAndDate(Long userId, LocalDate date);
    Claim findById(String id);
    Claim create(ClaimRequestDto request);
    Claim update(String id, ClaimRequestDto request);
}
