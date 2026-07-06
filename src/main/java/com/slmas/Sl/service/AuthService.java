package com.slmas.Sl.service;

import com.slmas.Sl.dto.request.LoginRequestDto;
import com.slmas.Sl.dto.response.AuthResponseDto;
import com.slmas.Sl.exceptions.NotFoundException;

public interface AuthService {
    public AuthResponseDto login (LoginRequestDto request) throws NotFoundException;
}
