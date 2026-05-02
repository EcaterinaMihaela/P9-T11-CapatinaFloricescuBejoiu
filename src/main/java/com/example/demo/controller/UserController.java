package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO dto) {
        return service.update(id, dto);
    }
    @PutMapping("/{id}/ban")
    public void ban(@PathVariable Long id, @RequestParam String reason) {
        service.banUser(id, reason);
    }

    @PutMapping("/{id}/unban")
    public void unban(@PathVariable Long id) {
        service.unbanUser(id);
    }

    @PatchMapping("/{id}/role")
    public User changeRole(@PathVariable Long id,
                           @RequestBody Map<String, String> body) {

        String role = body.get("role");
        return service.changeRole(id, role);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}