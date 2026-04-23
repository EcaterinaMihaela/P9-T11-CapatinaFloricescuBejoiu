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
                .orElse(null);

        if (user == null) {
            return new LoginResponseDTO(null, null, "FAIL");
        }

        if (!user.getPassword().equals(request.getPassword())) {
            return new LoginResponseDTO(null, null, "FAIL");
        }

        return new LoginResponseDTO(user.getUserid(), user.getUsername(), user.getRole());
    }


}