package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Long id);

    User create(UserDTO dto);

    User update(Long id, UserDTO dto);

    void delete(Long id);
}