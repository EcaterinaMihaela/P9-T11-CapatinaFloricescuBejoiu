package com.example.demo.service.impl;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final RepositoryWrapper repo;

    public AuthServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = repo.user.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Wrong username."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Wrong password.");
        }

        return new LoginResponseDTO(user.getUserid(), user.getUsername(), user.getRole());
    }
}