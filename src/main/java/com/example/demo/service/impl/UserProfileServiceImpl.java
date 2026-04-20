package com.example.demo.service.impl;

import com.example.demo.dto.UserProfileDTO;
import com.example.demo.model.User;
import com.example.demo.model.UserProfile;
import com.example.demo.repository.UserProfileRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository repo;
    private final UserRepository userRepository;

    public UserProfileServiceImpl(UserProfileRepository repo,
                                  UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserProfile> getAll() {
        return repo.findAll();
    }

    @Override
    public UserProfile getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public UserProfile create(UserProfileDTO dto) {

        User user = userRepository.findById(dto.getUserID())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = new UserProfile();
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setEmail(dto.getEmail());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setAddress(dto.getAddress());
        profile.setUser(user);

        return repo.save(profile);
    }

    @Override
    public UserProfile update(Long id, UserProfileDTO dto) {
        return repo.findById(id).map(profile -> {

            profile.setFirstName(dto.getFirstName());
            profile.setLastName(dto.getLastName());
            profile.setEmail(dto.getEmail());
            profile.setPhoneNumber(dto.getPhoneNumber());
            profile.setAddress(dto.getAddress());

            return repo.save(profile);
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}