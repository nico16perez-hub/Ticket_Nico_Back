package com.slmas.Sl.service.impl;

import com.slmas.Sl.domain.User;
import com.slmas.Sl.dto.request.LoginRequestDto;
import com.slmas.Sl.dto.response.AuthResponseDto;
import com.slmas.Sl.exceptions.NotFoundException;
import com.slmas.Sl.repository.UserRepository;
import com.slmas.Sl.service.AuthService;
import com.slmas.Sl.service.JwtService;
import com.slmas.Sl.utils.CryptoUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CryptoUtils cryptoUtils;


    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JwtService jwtService, CryptoUtils cryptoUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.cryptoUtils = cryptoUtils;
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) throws NotFoundException {

        String decryptedPassword;
        try {
            decryptedPassword = cryptoUtils.decrypt(request.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt password", e);
        }
        String userName = request.getUserName() == null ? null : request.getUserName().trim().toLowerCase(Locale.ROOT);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, decryptedPassword));

        User user = userRepository.findUserByName(userName);

        String token = jwtService.getToken(user);
        AuthResponseDto response = new AuthResponseDto();
        response.setUserName(user.getUsername());
        response.setToken(token);
        response.setRole(user.getRole());
        response.setName(user.getName());
        response.setSurname(user.getSurname());
        response.setId(user.getId());
        response.setArea(user.getArea());
        return response;
    }
}
