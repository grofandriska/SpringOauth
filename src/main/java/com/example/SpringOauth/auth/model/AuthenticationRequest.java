package com.example.SpringOauth.auth.model;

import lombok.*;

@Data
@Builder
public class AuthenticationRequest {
    private String email;
    String password;
}
