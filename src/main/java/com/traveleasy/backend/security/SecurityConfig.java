package com.traveleasy.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // disable CSRF (for React frontend)

            .authorizeHttpRequests(auth -> auth
                // ✅ allow login & register APIs
                .requestMatchers("/api/users/login", "/api/users/register").permitAll()

                // ✅ allow all other APIs for now
                .anyRequest().permitAll()
            )

            // ❌ disable default login page
            .formLogin(form -> form.disable())

            // ❌ disable basic auth popup
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
}