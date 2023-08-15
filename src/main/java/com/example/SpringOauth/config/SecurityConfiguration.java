package com.example.SpringOauth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    // When the application boots, spring looks for SecurityFilterChain @Bean -> Configuring all http securing

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .authorizeHttpRequests()
                // auth related things endpoints
                .requestMatchers(new AntPathRequestMatcher("/api/v1/auth/**"))
                // Whitelist all site
                .permitAll()
                // all request authenticated
                .anyRequest()
                .authenticated()
            .and()
                .sessionManagement()
                        // new session for each request
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authenticationProvider(authenticationProvider)
                //execute before userpass authentication (JWT FILTER) -> 1. set SecContext 2. -> do sec auth
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
