package com.example.game_verdict.auth;


import com.example.game_verdict.configuration.JwtService;
import com.example.game_verdict.entities.*;
import com.example.game_verdict.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.example.game_verdict.entities.Role.*;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(RegisterRequest request) {
        User user;

        switch (request.getRole()) {
            case ADMIN -> user = new Admin();
            case MEMBER -> user = new Member();
            case REVIEWER -> user = new Reviewer();
            case MODERATOR -> user = new Moderator();

            default -> throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        repository.save(user);

        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setCreationDate(user.getCreatedAt());
        return response;
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = repository.findByEmail(request.getEmail());
        String jwtToken = jwtService.generateToken(user);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setCreationDate(user.getCreatedAt());
        response.setLastLogin(user.getLastLogin());
        response.setBanned(user.isBanned());

        user.setLastLogin(LocalDateTime.now());
        repository.save(user);

        return response;
    }
}
