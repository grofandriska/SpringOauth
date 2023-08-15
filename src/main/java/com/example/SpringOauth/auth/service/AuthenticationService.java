package com.example.SpringOauth.auth.service;

import com.example.SpringOauth.auth.model.AuthenticationRequest;
import com.example.SpringOauth.auth.model.AuthenticationResponse;
import com.example.SpringOauth.auth.model.RegisterRequest;
import com.example.SpringOauth.user.config.JwtService;
import com.example.SpringOauth.user.model.Role;
import com.example.SpringOauth.user.model.User;
import com.example.SpringOauth.user.repo.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // create user save into DB and return token
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())).role(Role.USER)
                .build();
        repository.save(user);

        // not setting extra claims
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword())
        );
        //if code is here then authenticated and all data correct, so generate token and return
        //here get the exceptions

        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
