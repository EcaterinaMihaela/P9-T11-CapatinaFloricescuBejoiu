package com.example.demo.service;

import com.example.demo.dto.UserProfileDTO;
import com.example.demo.model.UserProfile;

import java.util.List;

public interface UserProfileService {

    List<UserProfile> getAll();

    UserProfile getById(Long id);

    UserProfile create(UserProfileDTO dto);

    UserProfile update(Long id, UserProfileDTO dto);

    void delete(Long id);
}