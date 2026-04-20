package com.example.demo.controller;

import com.example.demo.dto.UserProfileDTO;
import com.example.demo.model.UserProfile;
import com.example.demo.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserProfile> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserProfile getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public UserProfile create(@RequestBody UserProfileDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public UserProfile update(@PathVariable Long id,
                              @RequestBody UserProfileDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}