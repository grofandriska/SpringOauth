package com.example.SpringOauth.auth.model;


import lombok.*;

@Data
@RequiredArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
