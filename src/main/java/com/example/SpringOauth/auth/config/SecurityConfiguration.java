package com.example.SpringOauth.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//AbstractPreAuthenticatedProcessingFilter

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
								.requestMatchers(new AntPathRequestMatcher("/api/v1/auth/register")).permitAll()
								.requestMatchers(new AntPathRequestMatcher("/api/v1/auth/authenticate")).permitAll()
								.requestMatchers(new AntPathRequestMatcher("/api/v1/demo-controller")).hasAnyAuthority("GOD")
								.anyRequest().authenticated())
				.sessionManagement(sessionManagement -> sessionManagement
						.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
						.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::none))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, AbstractPreAuthenticatedProcessingFilter.class)
				.formLogin(form -> form
						.defaultSuccessUrl("/api/v1/demo-controller")
						.permitAll())
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Use the desired logout URL
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID") // Delete the session cookie
						.logoutSuccessUrl("/login")) // Redirect to login page after logout
				.headers().frameOptions().sameOrigin();
		
		
		return http.build();
	}
}
