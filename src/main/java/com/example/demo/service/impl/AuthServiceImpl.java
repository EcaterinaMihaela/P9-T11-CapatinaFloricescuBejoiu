package com.example.demo.service.impl;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.AuthService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.model.UserProfile;

@Service
public class AuthServiceImpl implements AuthService {

    private final RepositoryWrapper repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = repo.user.findByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            return new LoginResponseDTO(null, null, "FAIL", "OK");
        }

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponseDTO(null, null, "FAIL", "OK");
        }

        return new LoginResponseDTO(user.getUserid(), user.getUsername(), user.getRole(), "OK");
    }

    @Override
    public User register(RegisterRequestDTO dto) {

        // verificare username unic
        if (repo.user.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 🔐 create user
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole("USER");

        // 👤 create profile
        UserProfile profile = new UserProfile();
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setEmail(dto.getEmail());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setAddress(dto.getAddress());

        // 🔗 link bidirectional
        profile.setUser(user);
        user.setProfile(profile);

        return repo.user.save(user);
    }

}