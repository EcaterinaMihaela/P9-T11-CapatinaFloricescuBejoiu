package com.example.demo.service.impl;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.repository.impl.RepositoryWrapper;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final RepositoryWrapper repo;

    public UserServiceImpl(RepositoryWrapper repo) {
        this.repo = repo;
    }

    @Override
    public List<User> getAll() {
        return repo.user.findAll();
    }

    @Override
    public User getById(Long id) {
        return repo.user.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User create(UserDTO dto) {

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return repo.user.save(user);
    }

    @Override
    public User update(Long id, UserDTO dto) {

        User existing = getById(id);

        existing.setUsername(dto.getUsername());
        existing.setPassword(dto.getPassword());
        existing.setRole(dto.getRole());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return repo.user.save(existing);
    }

    @Override
    public void delete(Long id) {
        repo.user.deleteById(id);
    }
}