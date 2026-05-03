package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of("*"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
                    corsConfig.setAllowedHeaders(List.of("*"));
                    corsConfig.setAllowCredentials(false);
                    return corsConfig;
                }))
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
                                "/author-management.html",
                                "/publisher-management.html",
                                "/category-management.html",
                                "/librarian-ControlPanel.html",
                                "/reservations.html",
                                "/add-book.html",
                                "/edit-book.html",
                                "/library-statistics.html",
                                "/book-details.html",
                                "/librarian-reservationManagement.html",
                                "/librarian-loanManagement.html",
                                "/reviews-management.html",
                                "/js/**",
                                "/css/**",
                                "/auth/**",
                                "/users",
                                "/members",
                                "/librarians",
                                "/profiles/**",
                                "/authors",
                                "/authors/**",
                                "/publishers",
                                "/publishers/**",
                                "/categories",
                                "/categories/**",
                                "/publishers/**",
                                "/reports.html",
                                "/reports/**",
                                "/publishers/**",
                                "/reservations",
                                "/reservations/**",
                                "/loans.html",
                                "/loans",
                                "/loans/**",
                                "/reviews/",
                                "/reviews/**"


                        ).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers("/books", "/books/**").permitAll()
                        .requestMatchers("/api/books/**").permitAll()
                        .requestMatchers("/users/*/role").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authors/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/publishers/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/books").permitAll()
                        .requestMatchers(HttpMethod.POST, "/authors").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories").permitAll()
                        .requestMatchers(HttpMethod.POST, "/publishers").permitAll()

                        .requestMatchers(HttpMethod.PATCH, "/librarians/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/users/*/role").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/loans/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/loans/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable());


        return http.build();
    }
}