package com.example.demo.service.impl;

import com.example.demo.dto.UserProfileDTO;
import com.example.demo.model.User;
import com.example.demo.model.UserProfile;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.UserProfileService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final RepositoryWrapper repo;

    public UserProfileServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<UserProfile> getAll() {
        return repo.userProfile.findAll();
    }

    @Override
    public UserProfile getById(Long id) {
        return repo.userProfile.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Override
    public UserProfile create(UserProfileDTO dto) {

        User user = repo.user.findById(dto.getUserID())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfile profile = new UserProfile();
        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());
        profile.setEmail(dto.getEmail());
        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setAddress(dto.getAddress());
        profile.setUser(user);

        return repo.userProfile.save(profile);
    }

    @Override
    public UserProfile update(Long id, UserProfileDTO dto) {

        UserProfile profile = getById(id);

        profile.setFirstName(dto.getFirstName());
        profile.setLastName(dto.getLastName());

        if (dto.getEmail() != null) {
            profile.setEmail(dto.getEmail());
        }

        profile.setPhoneNumber(dto.getPhoneNumber());
        profile.setAddress(dto.getAddress());

        return repo.userProfile.save(profile);
    }

    @Override
    public void delete(Long id) {
        repo.userProfile.deleteById(id);
    }
}