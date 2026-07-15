package com.slmas.Sl.controller;

import com.slmas.Sl.dto.request.LoginRequestDto;
import com.slmas.Sl.dto.response.AuthResponseDto;
import com.slmas.Sl.exceptions.NotFoundException;
import com.slmas.Sl.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.slmas.Sl.domain.User;

import javax.naming.AuthenticationException;
import java.sql.PreparedStatement;

@RestController
@RequestMapping
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin
    @PostMapping({"/auth/login", "/api/auth/login"})
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {
        try {
            AuthResponseDto response = authService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contraseña incorrecta!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/api/auth/me")
    public ResponseEntity<AuthResponseDto> me(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User user)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        AuthResponseDto response = new AuthResponseDto();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setSurname(user.getSurname());
        response.setUserName(user.getUserName());
        response.setArea(user.getArea());
        response.setRole(user.getRole());
        return ResponseEntity.ok(response);
    }
}
