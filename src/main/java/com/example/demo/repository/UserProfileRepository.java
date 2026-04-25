package com.example.demo.repository;

import com.example.demo.model.UserProfile;

import java.util.Optional;

public interface UserProfileRepository extends BaseRepository<UserProfile, Long> {
    Optional<UserProfile> findByUser_Userid(Long userid);
}
