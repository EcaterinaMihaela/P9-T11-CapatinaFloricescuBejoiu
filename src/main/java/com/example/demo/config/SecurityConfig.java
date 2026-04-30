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
                                "/js/**",
                                "/css/**",
                                "/auth/**",
                                "/users",
                                "/profiles/**",
                                "/authors",
                                "/authors/**",
                                "/publishers",
                                "/publishers/**",
                                "/categories",
                                "/categories/**",
                                "/publishers/**",
                                "/add-book.html",
                                "/edit-book.html",
                                "/library-statistics.html"


                        ).permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                        .requestMatchers("/books", "/books/**").permitAll()
                        .requestMatchers("/api/books/**").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/authors/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/publishers/**").permitAll()

                                .requestMatchers(HttpMethod.POST, "/books").permitAll() // ADAUGĂ ASTA - Permite salvarea
                                .requestMatchers(HttpMethod.POST, "/authors").permitAll() // ADAUGĂ ASTA (opțional)
                                .requestMatchers(HttpMethod.POST, "/categories").permitAll() // ADAUGĂ ASTA (opțional)
                                .requestMatchers(HttpMethod.POST, "/publishers").permitAll() // ADAUGĂ ASTA (opțional)
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}