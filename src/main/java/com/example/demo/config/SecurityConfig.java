package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login.html",
                                "/register.html",
                                "/dashboard.html",
                                "/navbar.html",
                                "/about-us.html",
                                "/forgot-password.html",
                                "/admin-ControlPanel.html",
                                "/profile.html",
                                "/reset-password.html",
                                "/verify-code.html",
                                "/js/**",
                                "/css/**",
                                "/auth/**",
                                "/users",
                                "/profiles"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}